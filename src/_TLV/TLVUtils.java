package _TLV;

public class TLVUtils {

	/*
	 * The Below Method is used to check , if First Tag has a following byte by
	 * checking the last 5 bits , if they are set to 1 then the tag has a following
	 * byte else not
	 */
	public static boolean checkForFirstTagFollowingByte(String tag) {

		int andOperationResult = Integer.parseInt(tag, 16) & 0x1F;
		String binaryEquivalent = Integer.toBinaryString(andOperationResult);
		boolean result = binaryEquivalent.length() >= 5
				&& binaryEquivalent.substring(binaryEquivalent.length() - 5).equals("11111");
		return result;
	}

	/*
	 * The Below Method is used to check , if non first Tag has a following byte by
	 * checking the 8th bit , if it is set to 1 then the tag has a following byte
	 * else not
	 */
	public static boolean checkForNonFirstFollowingByte(String tag) {

		int andOperationResult = Integer.parseInt(tag, 16) & 0x80;
		String binaryEquivalent = Integer.toBinaryString(andOperationResult);
		boolean result = binaryEquivalent.length() >= 8
				&& binaryEquivalent.charAt(binaryEquivalent.length() - 8) == '1';
		return result;

	}

	public static String getTagByteFromHexTag(String hexTag) {
		// TODO Auto-generated method stub

		int i = 0;
		boolean hasFollowingTag = false;

		String firstTag = hexTag.substring(i, i + 2);
		hasFollowingTag = checkForFirstTagFollowingByte(firstTag);
		int k = i;
		while (hasFollowingTag && (k + 3) < hexTag.length()) {
			k = k + 2;
			hasFollowingTag = checkForNonFirstFollowingByte(hexTag.substring(k, k + 2));
		}

		return hexTag.substring(i, k + 2);
	}

	public static String getLengthByteOfTag(String hexTag) {
		// TODO Auto-generated method stub
		int i = 0;
		String lengthTag = hexTag.substring(i, i + 2);
		int decimalForm = Integer.parseInt(lengthTag, 16);
		String binaryEquivalent = Integer.toBinaryString(decimalForm);
		boolean isEightBitSet = binaryEquivalent.length() >= 8
				&& binaryEquivalent.charAt(binaryEquivalent.length() - 8) == '1';

		if (isEightBitSet) {
			int start = i + 2;
			int end = Integer.parseInt(Character.toString(lengthTag.charAt(1)), 16) * 2;
			return hexTag.substring(start, start + end);
		} else {
			return lengthTag;
		}

	}

	public static boolean isTagConstructed(String tag) {
		// TODO Auto-generated method stub
		int decimalEquivalent = Integer.parseInt(tag, 16);
		String binaryEquivalent = Integer.toBinaryString(decimalEquivalent);
		return binaryEquivalent.length() >= 6 && binaryEquivalent.charAt(binaryEquivalent.length() - 6) == '1';
	}
}
