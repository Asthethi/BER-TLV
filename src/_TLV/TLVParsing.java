package _TLV;

import java.util.ArrayList;
import java.util.List;

public class TLVParsing {

	/*
	 * Challange 1 : Check if the tag is valid or not, and print valid set of tags
	 */
	public static boolean isTagValid(String hexTag) {

		boolean doesFirstTagHasFollowingByte = false;
		List<String> validTags = new ArrayList<>();

		if (hexTag.length() % 2 != 0) {
			System.out.println("Tag Length cannot be Odd !!");
			return false;
		}

		for (int i = 0; i < hexTag.length(); i = i + 2) {
			String tag = hexTag.substring(0, i + 2);

			if (!doesFirstTagHasFollowingByte)
				doesFirstTagHasFollowingByte = TLVUtils.checkForFirstTagFollowingByte(tag);

			int k = i;
			while (doesFirstTagHasFollowingByte && (k + 3) < hexTag.length()) {
				k = k + 2;
				doesFirstTagHasFollowingByte = TLVUtils.checkForNonFirstFollowingByte(hexTag.substring(k, k + 2));

			}

			if (doesFirstTagHasFollowingByte) {
				System.out.println("Invalid Tag : " + hexTag
						+ " is expected to have a following tag byte , which is not present !!");
				return false;
			}

			validTags.add(hexTag.substring(i, k + 2));
			i = k;

		}

		System.out.println("Valid Set of Tags : " + validTags);

		return true;
	}

	/*
	 * Challange 2 : parse the TLV Tag , Length and Value from the given hex String
	 */
	private static List<TLVData> parseTLVLengthValue(String hexTag) {
		// TODO Auto-generated method stub
		List<TLVData> tlvData = new ArrayList<>();
		while (hexTag.length() > 0) {
			String tagByte = TLVUtils.getTagByteFromHexTag(hexTag);
			hexTag = hexTag.substring(hexTag.indexOf(tagByte) + tagByte.length());
			String lengthByte = TLVUtils.getLengthByteOfTag(hexTag);
			int tlvLengthDecimalForm = Integer.parseInt(lengthByte, 16);
			hexTag = hexTag.substring(hexTag.indexOf(lengthByte) + lengthByte.length());
			String tagValue = hexTag.substring(0, tlvLengthDecimalForm * 2);
			hexTag = hexTag.substring(tlvLengthDecimalForm * 2);

			tlvData.add(new TLVData(tagByte, tlvLengthDecimalForm, tagValue));
		}

		System.out.println(tlvData);
		return tlvData;
	}

	/*
	 * Challange 3 : parse the Constructed TLV Tag , Length and Value from the given
	 * hex String
	 */
	private static void parseConstructedTLVTag(String hexTag) {
		List<TLVData> parsedTlvTag = parseTLVLengthValue(hexTag);
		parsedTlvTag.forEach(t -> {
			if (TLVUtils.isTagConstructed(t.getTag())) {
				parseConstructedTLVTag(t.getValue());
			}
		});
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		isTagValid("9F26");
		System.out.println("--------------------------------");
		parseTLVLengthValue("9F26820001AA");
		System.out.println("--------------------------------");
		parseConstructedTLVTag("770B5A0101770677045A021122");
	}

}
