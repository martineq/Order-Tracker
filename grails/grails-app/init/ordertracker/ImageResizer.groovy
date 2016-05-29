package ordertracker

import ordertracker.internalServices.ImageServiceHelper
import ordertracker.internalServices.ResizeImageService

class ImageResizer {

    private String imagePath
    private String tempPath
    private String compressPath

    public ImageResizer(String imagePath, String tempPath, String compressPath) {
        this.imagePath = imagePath
        this.tempPath = tempPath
        this.compressPath = compressPath
    }

    public void resize() {
        this.createTmpFolder()
        ResizeImageService resizeImageService = new ResizeImageService(imagePath, compressPath)
        ImageServiceHelper imageServiceHelper = new ImageServiceHelper()

        try {
            new File(imagePath).eachFile() { file->
                resizeImageService.compressImage( file.getName() )
                imageServiceHelper.loadValues(new Integer(file.getName()), compressPath, tempPath)
                imageServiceHelper.loadImage()
            }
        }

        catch(FileNotFoundException e) {}
    }

    private void createTmpFolder() {
        try {
            new File(compressPath).mkdirs()
            new File(tempPath).mkdirs()
        }
        catch(FileNotFoundException e) {}
    }

}
