package com.bill99.mam.platform.common.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QrcodeUtil {
	
	private static final String DEFAULT_IMG_FORMAT = "png";
	
	private static final int BLACK = 0xff000000;
	private static final int WHITE = 0xF6F6F6F6;

	//图标宽度
	private static final int ICON_WIDTH = 68;
	//图标高度
	private static final int ICON_HEIGHT = 68;

	// 底图大小【正方形】
	private static final int ICON_HALF_WIDTH = ICON_WIDTH / 2;

	//底图边框
	private static final int FRAME_WIDTH = 1;

	//二维码写码器
	private static MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
	
	private final static Logger logger = LoggerFactory.getLogger(QrcodeUtil.class);

	public static BufferedImage encode(String contents, BarcodeFormat format, int width, int height) {
		try {
			//消除乱码
			contents = new String(contents.getBytes("UTF-8"), "ISO-8859-1");
			BitMatrix bitMatrix = multiFormatWriter.encode(contents, format, width, height);
			return toBufferedImage(bitMatrix);
		} catch (Exception e) {
			logger.warn("encode qrcode happened error!",e);
			return null;
		}
	}
	
	public static BufferedImage encode(String contents, BarcodeFormat format, int width, int height,
			Map<EncodeHintType, ?> hints) {
		try {
			//消除乱码
			contents = new String(contents.getBytes("UTF-8"), "ISO-8859-1");
			BitMatrix bitMatrix = multiFormatWriter.encode(contents, format, width, height);
			return toBufferedImage(bitMatrix);
		} catch (Exception e) {
			logger.warn("encode qrcode happened error!",e);
			return null;
		}
	}

	public static void encode(String contents, File file, BarcodeFormat format, int width, int height,
			Map<EncodeHintType, ?> hints) {
		try {
			//消除乱码
			contents = new String(contents.getBytes("UTF-8"), "ISO-8859-1");
			BitMatrix bitMatrix = multiFormatWriter.encode(contents, format, width, height);
			writeToFile(bitMatrix, DEFAULT_IMG_FORMAT, file);
		} catch (Exception e) {
			logger.warn("encode qrcode happened error!",e);
		}
	}

	public static void writeToFile(BitMatrix matrix, String format, File file) throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		ImageIO.write(image, format, file);
	}

	public static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) == true ? BLACK : WHITE);
			}
		}
		return image;
	}

	public static BufferedImage genQrcode(String content, int width, int height, String iconImagePath, boolean hasFiller)
			throws WriterException, IOException {
		// 读取源图像
		BufferedImage scaleImage = scale(iconImagePath, ICON_WIDTH, ICON_HEIGHT, hasFiller);
		int[][] srcPixels = new int[ICON_WIDTH][ICON_HEIGHT];
		for (int i = 0; i < scaleImage.getWidth(); i++) {
			for (int j = 0; j < scaleImage.getHeight(); j++) {
				srcPixels[i][j] = scaleImage.getRGB(i, j);
			}
		}

		Map<EncodeHintType, Object> hint = new HashMap<EncodeHintType, Object>();
		hint.put(EncodeHintType.CHARACTER_SET, "utf-8");
		hint.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		// 生成二维码
		BitMatrix matrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hint);

		// 二维矩阵转为一维像素数组
		int halfW = matrix.getWidth() / 2;
		int halfH = matrix.getHeight() / 2;
		int[] pixels = new int[width * height];

		for (int y = 0; y < matrix.getHeight(); y++) {
			for (int x = 0; x < matrix.getWidth(); x++) {
				// 读取图片
				if (x > halfW - ICON_HALF_WIDTH && x < halfW + ICON_HALF_WIDTH && y > halfH - ICON_HALF_WIDTH
						&& y < halfH + ICON_HALF_WIDTH) {
					pixels[y * width + x] = srcPixels[x - halfW + ICON_HALF_WIDTH][y - halfH + ICON_HALF_WIDTH];
				}
				// 在图片四周形成边框
				else if ((x > halfW - ICON_HALF_WIDTH - FRAME_WIDTH && x < halfW - ICON_HALF_WIDTH + FRAME_WIDTH
						&& y > halfH - ICON_HALF_WIDTH - FRAME_WIDTH && y < halfH + ICON_HALF_WIDTH + FRAME_WIDTH)
						|| (x > halfW + ICON_HALF_WIDTH - FRAME_WIDTH && x < halfW + ICON_HALF_WIDTH + FRAME_WIDTH
								&& y > halfH - ICON_HALF_WIDTH - FRAME_WIDTH && y < halfH + ICON_HALF_WIDTH
								+ FRAME_WIDTH)
						|| (x > halfW - ICON_HALF_WIDTH - FRAME_WIDTH && x < halfW + ICON_HALF_WIDTH + FRAME_WIDTH
								&& y > halfH - ICON_HALF_WIDTH - FRAME_WIDTH && y < halfH - ICON_HALF_WIDTH
								+ FRAME_WIDTH)
						|| (x > halfW - ICON_HALF_WIDTH - FRAME_WIDTH && x < halfW + ICON_HALF_WIDTH + FRAME_WIDTH
								&& y > halfH + ICON_HALF_WIDTH - FRAME_WIDTH && y < halfH + ICON_HALF_WIDTH
								+ FRAME_WIDTH)) {
					pixels[y * width + x] = WHITE;
				} else {
					// 此处可以修改二维码的颜色，可以分别制定二维码和背景的颜色；
					pixels[y * width + x] = matrix.get(x, y) ? BLACK : WHITE;
				}
			}
		}

		BufferedImage bufferedImage = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
		bufferedImage.getRaster().setDataElements(0, 0, width, height, pixels);
		return bufferedImage;
	}

	private static BufferedImage scale(String iconImagePath, int height, int width, boolean hasFiller)
			throws IOException {
		// 缩放比例
		double ratio = 0.0;
		File file = new File(iconImagePath);
		BufferedImage srcImage = ImageIO.read(file);
		Image destImage = srcImage.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);

		// 计算比例
		if ((srcImage.getHeight() > height) || (srcImage.getWidth() > width)) {

			if (srcImage.getHeight() > srcImage.getWidth()) {
				ratio = (new Integer(height)).doubleValue() / srcImage.getHeight();
			} else {
				ratio = (new Integer(width)).doubleValue() / srcImage.getWidth();
			}
			AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
			destImage = op.filter(srcImage, null);
		}

		//补白
		if (hasFiller) {
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D graphic = image.createGraphics();
			graphic.setColor(new Color(WHITE));
			graphic.fillRect(0, 0, width, height);
			if (width == destImage.getWidth(null)) {
				graphic.drawImage(destImage, 0, (height - destImage.getHeight(null)) / 2, destImage.getWidth(null),
						destImage.getHeight(null), new Color(WHITE), null);
			} else {
				graphic.drawImage(destImage, (width - destImage.getWidth(null)) / 2, 0, destImage.getWidth(null),
						destImage.getHeight(null), new Color(WHITE), null);
			}

			graphic.dispose();
			destImage = image;
		}

		return (BufferedImage) destImage;
	}
}
