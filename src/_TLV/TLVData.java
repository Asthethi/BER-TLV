package _TLV;

public class TLVData {

	public String tag;
	public int length;
	public String value;

	public TLVData(String tag, int length, String value) {
		super();
		this.tag = tag;
		this.length = length;
		this.value = value;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "TLVData [tag=" + tag + ", length=" + length + ", value=" + value + "]";
	}

}
