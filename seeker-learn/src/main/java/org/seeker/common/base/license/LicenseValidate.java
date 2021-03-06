//package org.seeker.common.base.license;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.ObjectInputStream;
//import java.text.SimpleDateFormat;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Vector;
//
//import org.apache.commons.codec.binary.Base64;
//import org.apache.log4j.Logger;
//import org.seeker.mapper.base.license.config.LicenseConfig;
//import org.seeker.mapper.base.license.entity.LicenseEntity;
//import org.seeker.mapper.util.BaseUtils;
//
//import com.smardec.license4j.License;
//import com.smardec.license4j.LicenseManager;
//import com.smardec.license4j.LicenseNotFoundException;
//import com.smardec.license4j.LicenseUtil;
//
//public class LicenseValidate {
//	protected Logger logger = Logger.getLogger(this.getClass().getName());
//
//	private String[] keys = null;
//	public License license;
//	private String licensePath = "";
//
//
//	public void LicenseValidate() {
//		try {
////			this.licensePath = getClass().getClassLoader().getResource("/").toURI().getPath();
//			this.licensePath=this.getClass().getResource("/").getPath()+"license"+File.separatorChar;
//
//			BaseUtils.print(licensePath);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		ParsingKeyPair();
//		ParsingLicense();
//		ValidateLicense();
//	}
//
//	private void ParsingKeyPair() {
//		FileInputStream fis = null;
//		try {
//			fis = new FileInputStream(this.licensePath + LicenseConfig.keyName);
//		} catch (FileNotFoundException e) {
////			System.out.println("未检测到license.key文件！");
//			System.exit(0);
//		}
//		try {
//			ObjectInputStream ois = new ObjectInputStream(fis);
//			this.keys = ((String[]) ois.readObject());
//			this.keys[0] = new String(Base64.decodeBase64(this.keys[0].getBytes()));
//			this.keys[1] = new String(Base64.decodeBase64(this.keys[1].getBytes()));
//			ois.close();
//		} catch (Exception ex) {
////			System.out.println("非法key！");
//			System.exit(0);
//		}
//	}
//
//	private void ParsingLicense() {
//		try {
//			LicenseManager.setSerializeStrings(true);
//
//			LicenseManager.setPublicKey(this.keys[0]);
//			this.license = LicenseManager.loadLicense(this.licensePath + LicenseConfig.licName);
//		} catch (LicenseNotFoundException e) {
//			e.printStackTrace();
//			System.exit(0);
//		}
//	}
//
//	private void ValidateLicense() {
//		try {
//			LicenseEntity entity=new LicenseEntity(license);
////			entity.toEntity();
//			if (LicenseManager.isValid(this.license)) {
//				if (checkMacAddress(entity.getMacAddress())) {
////					System.out.println("网卡校验通过！");
//				} else {
////					System.out.println("网卡校验不通过，系统将关闭！");
//					System.exit(0);
//				}
//
//				if ("Y".equals(entity.getRegistrationState())) {
////					System.out.println("License校验通过，已注册版本！");
//				} else if ("N".equals(entity.getRegistrationState())) {
//					try {
//						SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
//						Date date = myFormatter.parse(String.valueOf(entity.getExpiredDate()));
//						if (date.before(new Date())) {
////							System.out.print("License已过期，系统将关闭！");
//							System.exit(0);
//						}
//					} catch (Exception e) {
////						System.out.println("License校验过期时间异常，系统将关闭！");
//						System.exit(0);
//					}
//
//					String LastAccessTime = String.valueOf(this.license.getFeature("LastAccessTime"));
//					try {
//						SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//						Date date = myFormatter.parse(String.valueOf(entity.getLastAccessTime()));
//						if (date.after(new Date())) {
////							System.out.print("License上次访问时间晚于当前时间，系统将关闭！");
//							System.exit(0);
//						}
//					} catch (Exception e) {
////						System.out.println("License校验上次访问时间异常，系统将关闭！");
//						System.exit(0);
//					}
//
//					try {
//						if (Integer.parseInt(entity.getResidueDegree()) < 1) {
////							System.out.print("License超过最大使用次数，系统将关闭！");
//							System.exit(0);
//						}
//					} catch (Exception e) {
////						System.out.println("License校验最大使用次数异常，系统将关闭！");
//						System.exit(0);
//					}
//
//					System.out.println("License校验通过！未注册版本！");
//				}
//
//				UpdateLicense(this.license);
//			} else {
//				System.out.println("********************无效License，系统将关闭！********************");
//				System.exit(0);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	private void UpdateLicense(License license) {
//		try {
//			LicenseManager.setPrivateKey(this.keys[1]);
//
//			license.removeFeature("LastAccessTime");
//			Date date = new Date();
//			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//			String time = df.format(date);
//			license.addFeature("LastAccessTime", time);
//
//			if (String.valueOf(license.getFeature("RegistrationState")).equals("N")) {
//				int ResidueDegree = Integer.parseInt(String.valueOf(license.getFeature("ResidueDegree"))) - 1;
//				license.removeFeature("ResidueDegree");
//				license.addFeature("ResidueDegree", String.valueOf(ResidueDegree));
//			}
//
//			LicenseManager.saveLicense(license,licensePath+LicenseConfig.licName);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	private boolean checkMacAddress(String MacAddress) {
//		boolean result = false;
//		Vector macAddressVector = LicenseUtil.getMacAddresses();
//		List macAddressList = Arrays.asList(MacAddress.split(","));
//		for (int i = 0; i < macAddressVector.size(); i++) {
//			if (macAddressList.contains(macAddressVector.get(i))) {
//				result = true;
//				break;
//			}
//		}
//		return result;
//	}
//
//	private void printMacAddress() {
//		Vector macs = LicenseUtil.getMacAddresses();
//		Iterator i = macs.iterator();
//
//		StringBuffer buffer = new StringBuffer();
//
//		while (i.hasNext()) {
//			buffer.append(i.next() + ",");
//		}
//
//		System.out.println("网卡地址:");
//		System.out.println(buffer.substring(0, buffer.length() - 1));
//	}
//}