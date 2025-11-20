package image

import java.awt.image.BufferedImage

object ConfigImages {

    fun getSqmFinderHorizontalImage() : BufferedImage = ImageUtils.loadImageFromResources("sqm_finder_horizontal.png")
    fun getFishPuzzleImage() : BufferedImage = ImageUtils.loadImageFromResources("fish_puzzle.png")
    fun getFishTemplateImage() : BufferedImage = ImageUtils.loadImageFromResources("fish_template.png")

}