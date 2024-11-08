package yee.pltision.randomadditions.datagen;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

@SuppressWarnings("ALL")    //Touch grass!
public class Grass {
    //生成随机草
    public static void main(String[] args) throws IOException {
        int[] colors={/*0x22c878,0x3cbe78,*/0x50dca0/*,0x3cb196*/, 0x64f0a0,0x5ae6b4,0x5adcc8,0x8cf0c8};

        BufferedImage image=new BufferedImage(16,16,BufferedImage.TYPE_INT_ARGB);

        Random seedRandom=new Random();
        for(int ii=0;ii<4;ii++){
            short seed= (short) seedRandom.nextInt(Short.MAX_VALUE+1);
            System.out.println(seed);
            Random random=new Random(seed);
            for(int i=0;i<16;i++){
                for(int j=0;j<16;j++){
                    image.setRGB(i,j,colors[random.nextInt(colors.length)]|0xff000000);
                }
            }
            File file=new File("./image_output/cloud_grass"+seed+".png");
            file.createNewFile();
            ImageIO.write(image,"png",file);
        }
    }
}
