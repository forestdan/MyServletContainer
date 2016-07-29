package my.chapter03.connector;

public class HttpHeader {

	public static final int INITIAL_NAME_SIZE = 32;
	public static final int INITIAL_VALUE_SIZE = 64;
	public static final int MAX_NAME_SIZE = 128;
	public static final int MAX_VALUE_SIZE = 4096;

	public char[] name;
	public int nameEnd;
	public char[] value;
	public int valueEnd;
	protected int hashCode = 0;
	
	public HttpHeader() {
		this(new char[INITIAL_NAME_SIZE], 0, new char[INITIAL_VALUE_SIZE], 0);
	}

	public HttpHeader(char[] name, int nameEnd, char[] value, int valueEnd) {
		this.name = name;
		this.nameEnd = nameEnd;
		this.value = value;
		this.valueEnd = valueEnd;
	}
	
	public HttpHeader(String name, String value) {
        this.name = name.toLowerCase().toCharArray();
        this.nameEnd = name.length();
        this.value = value.toCharArray();
        this.valueEnd = value.length();
    }
	
	/**
     * Release all object references, and initialize instance variables, in
     * preparation for reuse of this object.
     */
    public void recycle() {
        nameEnd = 0;
        valueEnd = 0;
        hashCode = 0;
    }
    
	/**
     * Return hash code. The hash code of the HttpHeader object is the same
     * as returned by new String(name, 0, nameEnd).hashCode().
     */
    public int hashCode() {
        int h = hashCode;
        if (h == 0) {
            int off = 0;
            char val[] = name;
            int len = nameEnd;
            for (int i = 0; i < len; i++)
                h = 31*h + val[off++];
            hashCode = h;
        }
        return h;
    }
}
