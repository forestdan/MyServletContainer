package my.chapter03.connector;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.catalina.util.StringManager;

/**
 * 此类在tomcat4之后就不存在了，自己实现
 *
 */
public class SocketInputStream {

	// -------------------------------------------------------------- Constants

	/**
	 * CR.
	 */
	private static final byte CR = (byte) '\r';

	/**
	 * LF.
	 */
	private static final byte LF = (byte) '\n';

	/**
	 * SP.
	 */
	private static final byte SP = (byte) ' ';

	/**
	 * HT.
	 */
	private static final byte HT = (byte) '\t';

	/**
	 * COLON.
	 */
	private static final byte COLON = (byte) ':';

	/**
	 * Lower case offset.
	 */
	private static final int LC_OFFSET = 'A' - 'a';

	/**
	 * Last valid byte.
	 */
	protected int count;

	/**
	 * Position in the buffer.
	 */
	protected int pos;

	// 输入流
	protected InputStream in;

	// 内部缓冲
	protected byte[] buff;

	public SocketInputStream(InputStream in, int bufferSize) {
		this.in = in;
		buff = new byte[bufferSize];
	}

	protected static StringManager sm = StringManager.getManager(Constants.Package);

	public void readHeader(HttpHeader header) throws IOException {

		// 回收检查
		if (header.nameEnd != 0) {
			header.recycle();
		}

		int chr = read();

		if ((chr == CR) || (chr == LF)) { // Skipping CR
			if (chr == CR) {
				read(); // Skipping LF
			}
			header.nameEnd = 0;
			header.valueEnd = 0;
			return;
		} else {
			pos--;
		}

		// 读取header中的name,读取过程类似于读取request
		int maxRead = header.name.length;
		int readStart = pos;
		int readCount = 0;

		// 判断是否已经读取到冒号
		boolean colon = false;

		while (!colon) {
			if (readCount >= maxRead) {
				if ((2 * maxRead) <= HttpHeader.MAX_NAME_SIZE) {
					char[] newBuffer = new char[2 * maxRead];
					System.arraycopy(header.name, 0, newBuffer, 0, maxRead);
					header.name = newBuffer;
					maxRead = header.name.length;
				} else {
					throw new IOException(sm.getString("requestStream.readline.toolong"));
				}
			}
			// We're at the end of the internal buffer
			if (pos >= count) {
				int val = read();
				if (val == -1) {
					throw new IOException(sm.getString("requestStream.readline.error"));
				}
				pos = 0;
				readStart = 0;
			}
			if (buff[pos] == COLON) {
				colon = true;
			}
			char val = (char) buff[pos];
			if ((val >= 'A') && (val <= 'Z')) {
				val = (char) (val - LC_OFFSET);
			}
			header.name[readCount] = val;
			readCount++;
			pos++;
		}

		header.nameEnd = readCount - 1;

		// 开始读取header的value，此值有可能会换行
		maxRead = header.value.length;
		readStart = pos;
		readCount = 0;

		// end of line
		boolean eol = false;
		boolean validLine = true;

		while (validLine) {
			boolean space = true;
			// 此循环主要是用来跳过缓冲区前的空格或者制表符
			while (space) {
				if (pos >= count) {
					int val = read();
					if (val == -1)
						throw new IOException(sm.getString("requestStream.readline.error"));
					pos = 0;
					readStart = 0;
				}
				// 如果是空格或者制表符的话，就不读，当前读取位置pos + 1
				if ((buff[pos] == SP) || (buff[pos] == HT)) {
					pos++;
				} else {
					space = false;
				}
			}

			while (!eol) {
				if (readCount >= maxRead) {
					if ((2 * maxRead) <= HttpHeader.MAX_VALUE_SIZE) {
						char[] newBuffer = new char[2 * maxRead];
						System.arraycopy(header.value, 0, newBuffer, 0, maxRead);
						header.value = newBuffer;
						maxRead = header.value.length;
					} else {
						throw new IOException(sm.getString("requestStream.readline.toolong"));
					}
				}
				// We're at the end of the internal buffer
				if (pos >= count) {
					// Copying part (or all) of the internal buffer to the line
					// buffer
					int val = read();
					if (val == -1)
						throw new IOException(sm.getString("requestStream.readline.error"));
					pos = 0;
					readStart = 0;
				}

				// 如果遇到了换行就说明当前行已经到头了，如果不是换行，就读入value
				if (buff[pos] == CR) {
				} else if (buff[pos] == LF) {
					eol = true;
				} else {
					int ch = buff[pos] & 0xff;
					header.value[readCount] = (char) ch;
					readCount++;
				}
				pos++;
			}
			
			//获取下一个字符
			int nextChr = read();
			
			//如果这个字符不是空格或者制表，就说明到这里之前就已经读取完一对name value了，如果是空格或者制表的话，就说明value的值还没有读取完（Http协议约定）
			if ((nextChr != SP) && (nextChr != HT)) {
                pos--;
                validLine = false;
            } else {
                eol = false;
                // if the buffer is full, extend it
                if (readCount >= maxRead) {
                    if ((2 * maxRead) <= HttpHeader.MAX_VALUE_SIZE) {
                        char[] newBuffer = new char[2 * maxRead];
                        System.arraycopy(header.value, 0, newBuffer, 0,
                                         maxRead);
                        header.value = newBuffer;
                        maxRead = header.value.length;
                    } else {
                        throw new IOException
                            (sm.getString("requestStream.readline.toolong"));
                    }
                }
                header.value[readCount] = ' ';
                readCount++;
            }
		}
		header.valueEnd = readCount;
	}

	public void readRequestLine(HttpRequestLine requestLine) throws IOException {

		// 回收检查
		if (requestLine.methodEnd != 0) {
			requestLine.recycle();
		}

		// 检查空白行
		int chr = 0;
		// 循环检查缓冲区数据，如果是换行的话就一直循环读取，直到不是的时候停止，此时初始读取数据的位置pos则指向有实际数据的第一个字符
		do {
			try {
				chr = read();
			} catch (IOException e) {
				chr = -1;
			}
		} while ((chr == CR) || (chr == LF));
		if (chr == -1) {
			throw new EOFException(sm.getString("requestStream.readline.error"));
		}
		pos--;

		// 开始读取method
		// 最大读取长度
		int maxRead = requestLine.method.length;
		int readStart = pos;
		int readCount = 0;

		boolean space = false;

		while (!space) {
			// 当读取的字符数已经大于method的最大长度（初始化时指定的）时，需要扩充长度
			if (readCount >= maxRead) {
				if ((2 * maxRead) <= HttpRequestLine.MAX_METHOD_SIZE) {
					char[] newBuffer = new char[2 * maxRead];
					System.arraycopy(requestLine.method, 0, newBuffer, 0, maxRead);
					requestLine.method = newBuffer;
					maxRead = requestLine.method.length;
				} else {
					throw new IOException(sm.getString("requestStream.readline.toolong"));
				}
			}
			// 和read方法一样，表示已经读取完了当前的缓冲区，需要填满缓冲区
			if (pos >= count) {
				int val = read();
				if (val == -1) {
					throw new IOException(sm.getString("requestStream.readline.error"));
				}
				pos = 0;
				readStart = 0;
			}
			// 如果遇到了空格，就停止读取
			if (buff[pos] == SP) {
				space = true;
			}
			// 将读取到的当前字符存入HttpRequstline中
			requestLine.method[readCount] = (char) buff[pos];
			// 读取当前对象（在此时method）索引+1
			readCount++;
			// 缓冲区读取位置索引+1
			pos++;
		}
		requestLine.methodEnd = readCount - 1;

		// 开始读取uri，解析原理和method差不多，这里对http 0.9进行了特殊处理
		maxRead = requestLine.uri.length;
		readStart = pos;
		readCount = 0;

		space = false;

		boolean eol = false;

		while (!space) {
			// if the buffer is full, extend it
			if (readCount >= maxRead) {
				if ((2 * maxRead) <= HttpRequestLine.MAX_URI_SIZE) {
					char[] newBuffer = new char[2 * maxRead];
					System.arraycopy(requestLine.uri, 0, newBuffer, 0, maxRead);
					requestLine.uri = newBuffer;
					maxRead = requestLine.uri.length;
				} else {
					throw new IOException(sm.getString("requestStream.readline.toolong"));
				}
			}
			// We're at the end of the internal buffer
			if (pos >= count) {
				int val = read();
				if (val == -1)
					throw new IOException(sm.getString("requestStream.readline.error"));
				pos = 0;
				readStart = 0;
			}
			if (buff[pos] == SP) {
				space = true;
			} else if ((buff[pos] == CR) || (buff[pos] == LF)) {
				// HTTP/0.9 style request
				eol = true;
				space = true;
			}
			requestLine.uri[readCount] = (char) buff[pos];
			readCount++;
			pos++;
		}

		requestLine.uriEnd = readCount - 1;

		// 开始读取协议

		maxRead = requestLine.protocol.length;
		readStart = pos;
		readCount = 0;

		while (!eol) {
			// if the buffer is full, extend it
			if (readCount >= maxRead) {
				if ((2 * maxRead) <= HttpRequestLine.MAX_PROTOCOL_SIZE) {
					char[] newBuffer = new char[2 * maxRead];
					System.arraycopy(requestLine.protocol, 0, newBuffer, 0, maxRead);
					requestLine.protocol = newBuffer;
					maxRead = requestLine.protocol.length;
				} else {
					throw new IOException(sm.getString("requestStream.readline.toolong"));
				}
			}
			// We're at the end of the internal buffer
			if (pos >= count) {
				// Copying part (or all) of the internal buffer to the line
				// buffer
				int val = read();
				if (val == -1)
					throw new IOException(sm.getString("requestStream.readline.error"));
				pos = 0;
				readStart = 0;
			}
			if (buff[pos] == CR) {
				// Skip CR.
			} else if (buff[pos] == LF) {
				eol = true;
			} else {
				requestLine.protocol[readCount] = (char) buff[pos];
				readCount++;
			}
			pos++;
		}

		requestLine.protocolEnd = readCount;

	}

	/**
	 * 读取缓冲区的数据，当前位置还没有读取到超过缓冲数据时，就直接从缓冲数据中获取数据，如果超过了，就重新填缓冲区
	 * 
	 * @return
	 * @throws IOException
	 */
	private int read() throws IOException {
		if (pos >= count) {
			fill();
			if (pos >= count)
				return -1;
		}
		return buff[pos++] & 0xff;
	}

	/**
	 * 用来填满缓冲数据
	 * 
	 * @throws IOException
	 */
	protected void fill() throws IOException {
		pos = 0;
		count = 0;
		int nRead = in.read(buff, 0, buff.length);
		if (nRead > 0) {
			count = nRead;
		}
	}

}
