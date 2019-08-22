/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package network.tcp.replacement;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

/**
 *
 * @author Jacob
 */
public enum TiffTagModificationUtil {
    INSTANCE;

    public static void main(String[] args) throws FileNotFoundException, IOException {
        final File testImage = new File("C:\\Users\\Jacob\\Desktop\\TestPhoto.PC00");

        final FileInputStream inStream = new FileInputStream(testImage);

        final BufferedInputStream bin = new BufferedInputStream(inStream);
        bin.mark((int) testImage.length());

        byte[] endianBytes = TiffTagModificationUtil.INSTANCE.getEndianBytes(bin);
        System.out.println(String.format("Endian (I=little): %s", (char) endianBytes[0]));

        bin.reset();
        int ifdOffset = TiffTagModificationUtil.INSTANCE.getIfdOffsetBytes(bin, 0);
        System.out.println(String.format("IFD Offset for page %s is %d", 0, ifdOffset));

        inStream.close();
    }

    protected byte[] getEndianBytes(final BufferedInputStream bin) throws IOException {

        return getBytes(bin, 0, 2);
    }

    protected int getIfdOffsetBytes(final BufferedInputStream bin, final int page)
            throws IOException {

        byte[] offsetBytes = getBytes(bin, 4, 4);

        return convertBytesToInt(offsetBytes, true);
    }

    public byte[] getBytes(final BufferedInputStream bin, int offset, int length) throws IOException {

        byte[] offsetBytes = new byte[length];

        bin.reset();

        bin.skip(offset);

        bin.read(offsetBytes);

        return offsetBytes;
    }

    protected int convertBytesToInt(final byte[] bytes, final boolean littleEndian) {

        int intOut = 0;

        for (int i = 0; i < bytes.length; i++) {

            byte thisByte = littleEndian
                    ? bytes[i]
                    : bytes[bytes.length - i];

            intOut |= (Byte.toUnsignedInt(thisByte)) << (8 * i);
        }

        return intOut;
    }
//
//    protected void getTagBytesViaTagNum(final byte[] byteIn, final int tagNum) {
//        final byte[] ifdBytes = new byte[12];
//
//        ByteArrayInputStream bytesIn = new ByteArrayInputStream(byteIn);
//
//        bytesIn.read ByteArrayBuffer buff = new BufferedOuptu();
//        buff.append(byteIn);
//        buff.
//        int ifdOffset = byteIn[4];
//
//    }
}
