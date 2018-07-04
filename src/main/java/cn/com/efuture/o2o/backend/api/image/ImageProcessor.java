package cn.com.efuture.o2o.backend.api.image;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.Thumbnails.Builder;
import net.coobird.thumbnailator.geometry.Positions;

@Component
public class ImageProcessor {
	protected org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${image.quality}")
	private Double quality;

	public byte[] reSize(byte[] fromByte, String fileType, int tragetW, int tragetH) throws IOException {
		// create temp file
		File tempFile = File.createTempFile("img", fileType);
		try {
			FileOutputStream fOut = new FileOutputStream(tempFile);
			fOut.write(fromByte);
			fOut.close();
			Builder<File> thumb = Thumbnails.of(tempFile).scale(1.0);
			BufferedImage src = thumb.asBufferedImage();
			int h = src.getHeight(null);
			int w = src.getWidth(null);

			if (h < tragetH || w < tragetW) {
				throw new RuntimeException("图片像素过小请重新选择图片，要求最小大小：" + tragetW + "X" + tragetH);
			}

			if (h != w) {
				// 先裁剪为正方形图片
				int newSzie = w > h ? h : w;
				Thumbnails.of(tempFile).sourceRegion(Positions.CENTER, newSzie, newSzie).size(newSzie, newSzie)
						.toFile(tempFile);
			}

			if (quality == null)
				quality = 1.0;

			if (tragetH == tragetW && h > tragetH) {
				Thumbnails.of(tempFile).size(tragetW, tragetH).outputQuality(quality).outputFormat("jpg").toFile(tempFile);
			} else if (h > tragetH) {
				// 调整图片到规定的大小
				int newSize = tragetW < tragetH ? tragetW : tragetH;
				Thumbnails.of(tempFile).size(newSize, newSize).toFile(tempFile);
				// 白底画布
				BufferedImage white_pic = new BufferedImage(tragetW, tragetH, BufferedImage.TYPE_INT_RGB);
				Graphics2D graphics = (Graphics2D) white_pic.getGraphics();
				graphics.setBackground(Color.white);
				graphics.fillRect(0, 0, tragetW, tragetH);
				// 将图片嵌入到白底画布中
				Thumbnails.of(white_pic).size(tragetW, tragetH).watermark(Positions.CENTER, ImageIO.read(tempFile), 1).outputQuality(quality).outputFormat("jpg").toFile(tempFile);
			}

			FileInputStream fIn = new FileInputStream(tempFile);
			ByteArrayOutputStream outByte = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024 * 4];

			int n = 0;
			while ((n = fIn.read(buffer)) != -1) {
				outByte.write(buffer, 0, n);
			}
			fIn.close();

			return outByte.toByteArray();
		} finally {
			 tempFile.delete();
		}
	}
}
