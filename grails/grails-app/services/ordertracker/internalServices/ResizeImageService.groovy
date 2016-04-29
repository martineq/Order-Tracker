package ordertracker.internalServices

import ordertracker.constants.Sizes
import javax.imageio.IIOImage
import javax.imageio.ImageIO
import javax.imageio.ImageWriteParam
import javax.imageio.ImageWriter
import javax.imageio.stream.FileImageOutputStream
import java.awt.Color
import java.awt.Graphics
import java.awt.Image
import java.awt.image.BufferedImage

class ResizeImageService {

    private String inputDir
    private String outputDir
    private int MAX_WIDTH = Sizes.MAX_IMAGE_WIDTH.getInt()
    private int MAX_HEIGHT = Sizes.MAX_IMAGE_HEIGHT.getInt()

    public static ResizeImageService newInstance(String inputDir, String outputDir) {
        return new ResizeImageService( inputDir, outputDir);
    }

    public ResizeImageService(String inputDir, String outputDir) {
        this.inputDir = inputDir
        this.outputDir = outputDir
    }

    // compression parameters
    private void establishParameters(ImageWriteParam imageWriteParam) {
        imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        imageWriteParam.setCompressionQuality(0);
        imageWriteParam.setProgressiveMode(ImageWriteParam.MODE_DISABLED);
        imageWriteParam.setCompressionType(imageWriteParam.getCompressionTypes().first());
    }

    private int[] getScale(int width, int height) {
        int x = new Float(( width * MAX_WIDTH ) / height).trunc()
        int y = new Float(( height * MAX_HEIGHT ) / width).trunc()

        int newWidth = MAX_WIDTH
        int newHeight = MAX_HEIGHT

        if ( x > y ) {

            if ( x <= MAX_WIDTH ) {
                newWidth = x
                return Arrays.asList(newWidth, newHeight)

            } else if ( y <= MAX_HEIGHT) {
                newWidth = MAX_WIDTH
                newHeight = y
                return Arrays.asList(newWidth, newHeight)
            } else {
                throw new RuntimeException()
            }
        } else if ( y <= MAX_HEIGHT ) {
            newWidth = MAX_WIDTH
            newHeight = y
            return Arrays.asList(newWidth, newHeight)
        } else if ( x <= MAX_WIDTH ) {
            newWidth = x
            newHeight = MAX_HEIGHT
            return Arrays.asList(newWidth, newHeight)
        } else {
            return new RuntimeException()
        }
    }

    public boolean compressImage(String fileName) {
        boolean compressionResult = false;

        try {
            // input file
            String inputFileLocation = inputDir + fileName

            BufferedImage image = ImageIO.read(new File(inputFileLocation));

            Image scaledImage = null
            try {
                int[] newScale = this.getScale(image.getWidth(), image.getHeight());
                scaledImage = image.getScaledInstance(newScale[0], newScale[1], Image.SCALE_FAST);
            }
            catch (RuntimeException e) {
                scaledImage = image
            }

            // width and height are of the toolkit image
            BufferedImage newScaledImage = new BufferedImage(MAX_WIDTH, MAX_HEIGHT, BufferedImage.TYPE_INT_RGB);

            Graphics graphics = newScaledImage.getGraphics();

            graphics.setColor(Color.WHITE)
            graphics.fillRect(0, 0, MAX_WIDTH, MAX_HEIGHT)
            graphics.drawRect(0, 0, MAX_WIDTH, MAX_HEIGHT)

            int xPosition = (scaledImage.getWidth() == MAX_WIDTH ? 0 : new Float((MAX_WIDTH - scaledImage.getWidth()) / 2.0).trunc())
            int yPosition = (scaledImage.getHeight() == MAX_HEIGHT ? 0 : new Float((MAX_HEIGHT - scaledImage.getHeight()) / 2.0).trunc())

            graphics.drawImage(scaledImage, xPosition, yPosition, null);

            graphics.setColor(Color.LIGHT_GRAY)
            graphics.drawRect(0, 0, MAX_WIDTH - 1, MAX_HEIGHT - 1)

            graphics.dispose();
            IIOImage iioImage = new IIOImage(newScaledImage, null, null);

            // output file
            String outputFileLocation = outputDir + fileName
            FileImageOutputStream outputFile = new FileImageOutputStream(new File(outputFileLocation));

            // image writer
            Iterator<ImageWriter> imageWriters = ImageIO.getImageWritersByFormatName("jpeg");
            imageWriters.each { imageWriter ->

                ImageWriteParam imageWriterParam = this.establishParameters(imageWriter.getDefaultWriteParam());
                imageWriter.setOutput(outputFile);
                imageWriter.write(null, iioImage, imageWriterParam);
            }

            compressionResult = true;
        }
        catch (FileNotFoundException e) {}
        catch (NullPointerException e) {}

        return compressionResult;
    }


}
