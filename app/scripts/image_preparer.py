from PIL import Image

from resizeimage import resizeimage

import os
import sys

def resize_image(filename,folder,subfolder,factor,dp):
    size = dp * factor
    with open(os.path.join(folder,filename), 'r+b') as f:
    	with Image.open(f) as image:
                cover = resizeimage.resize_cover(image, [size, size])
        	cover.save(os.path.join("../src/main/res",subfolder,filename), image.format)
    
if __name__ == "__main__":
	folder = sys.argv[1]
	filename = sys.argv[2]
	dp = sys.argv[3]
	resize_image(filename,folder,"drawable-xxxhdpi",4, int(dp))
	resize_image(filename,folder,"drawable-xxhdpi", 3, int(dp))
	resize_image(filename,folder,"drawable-xhdpi", 2, int(dp))
	resize_image(filename,folder,"drawable-hdpi", 1.5, int(dp))
        resize_image(filename,folder,"drawable-mdpi", 1, int(dp))
