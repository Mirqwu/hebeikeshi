package erweima;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.swetake.util.Qrcode;

public class Tdcode {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		int version=8;//公式=((n-1)*4+21)*矩形长度,n为二维码版本，公式是二维码的画布大小可以填满
		
		int imageSize=((version-1)*4+21)*3;
	     
		Qrcode qrcode=new Qrcode();
		qrcode.setQrcodeVersion(version);
		qrcode.setQrcodeErrorCorrect('H');
		qrcode.setQrcodeEncodeMode('B');
		String content="http://www.dijiaxueshe.com";
		byte[] data=content.getBytes("utf-8");//需要抛出异常
		boolean[][]qrdata=qrcode.calQrcode(data);
		//设置图片缓冲
		BufferedImage bufferedImage=new BufferedImage(imageSize,imageSize,BufferedImage.TYPE_INT_BGR);
		//图片绘图
		Graphics2D gs=bufferedImage.createGraphics();
		//设置背景色
		gs.setBackground(Color.WHITE);
		
		//清楚画布
		int startR=255,startG=0,startB=0;
		int endR=0,endG=255,endB=0;
		gs.clearRect(0, 0, imageSize, imageSize);
		for(int i=0;i<qrdata.length;i++){
			
			for(int j=0;j<qrdata.length;j++){
				if(qrdata[i][j]){
					gs.fillRect(i*3, j*3, 3, 3);
					//设置二维码渐变
					int num1=startR+(endR-startR)*(i+1)/2/qrdata.length;
					int num2=startG+(endG-startG)*(i+1)/2/qrdata.length;
					int num3=startB+(endB-startB)*(i+1)/2/qrdata.length;
					Color color=new Color(num1,num2,num3);
					gs.setColor(color);
				}
			   }
			}
	
		BufferedImage logo=scale("D:/logo.jpg",60,60,true);//增加一张图片
		
		 //设置图片logo的中间
		int centent=(imageSize-logo.getHeight())/2;
		gs.drawImage(logo, centent, centent, 60, 60,null );
		gs.dispose();
		bufferedImage.flush();
		try {
			ImageIO.write(bufferedImage, "png", new File("D:/qrcode.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("有毛病");
		}
		System.out.println("二维码生成");
	}
	
	
	  private static BufferedImage scale(String logoPath, int width, int height, boolean hasFiller) throws IOException {
		// TODO Auto-generated method stub
     
	  double ratio = 0.0;//缩放比例
	  File file = new File (logoPath);
	  BufferedImage srcImage=ImageIO.read(file);
	  Image destImage = srcImage.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH) ;
	  // 计算比例
	  if((srcImage.getHeight() > height) || (srcImage.getWidth() > width)){
	     if (srcImage.getHeight() > srcImage.getWidth()){
	         ratio = (new Integer (height) ).doubleValue() / srcImage.getHeight();
	  }
	  else {
	        ratio = (new Integer(width)).doubleValue() / srcImage.getWidth();
	  }
	  AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio),null);
	  destImage = op.filter(srcImage, null) ;
	  }
	  if (hasFiller) {// 补白
	  BufferedImage image = new BufferedImage (width, height, BufferedImage.TYPE_INT_BGR);
	  Graphics2D graphic = image.createGraphics();
	  graphic.setColor(Color.white);
	  graphic.fillRect(0, 0, width, height) ;
	  if (width == destImage.getWidth(null)){
	  graphic.drawImage(destImage, 0,(height-destImage.getHeight (null))/2, destImage.getWidth(null),
	  destImage.getHeight (null),Color.white, null) ;
	  }else {
	       graphic.drawImage(destImage,(width-destImage.getWidth(null)) / 2, 0, destImage.getWidth(null),
	  destImage.getHeight(null),Color.white,null) ;
	  }
	  graphic.dispose();
	  destImage = image;
	  }
	  
	  
	  return (BufferedImage)destImage; 

	  
	
	  }
	  }


	


