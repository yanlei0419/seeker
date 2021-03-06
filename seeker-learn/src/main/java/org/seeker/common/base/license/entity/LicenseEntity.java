//package org.seeker.common.base.license.entity;
//
//import org.seeker.mapper.util.BaseUtils;
//
//import com.smardec.license4j.License;
//
//public class LicenseEntity {
//	private  String ExpiredDate   ;//license过期时间1
//	private  String CreateTime  ;//创建时间2
//	private  String LastAccessTime;//最后启动时间3
//	private  String SystemName   ;//系统名称4
//	private  String MacAddress   ;//mac地址5
//	private  String RegistrationState   ;//是否注册版本Y N 6
//
//	private  String Customer  ;//客户名称7
//	private  String Company   ;//名称8
//	private  String MajorVersion  ;//版本好9
//	private  String ResidueDegree  ;//最大重启次数10
//	private  String MinorVersion   ;//版本好11
//
//	private  String AuthorName  ;//作者姓名12
//	private  String email  ;//邮箱13
//
//
//	public LicenseEntity() {
//		super();
//	}
//
//	public License toLicense(){
//		License license=new License();
//		license.addFeature("Customer", Customer);
//		license.addFeature("Company", Company);
//		license.addFeature("SystemName", SystemName);
//		license.addFeature("MajorVersion", MajorVersion);
//		license.addFeature("MinorVersion", MinorVersion);
//		license.addFeature("CreateTime", CreateTime);
//		license.addFeature("RegistrationState", RegistrationState);
//		license.addFeature("MacAddress", MacAddress);
//		license.addFeature("AuthorName", AuthorName);
//		license.addFeature("email", email);
//		license.addFeature("ExpiredDate", ExpiredDate);
//		license.addFeature("LastAccessTime", LastAccessTime);
//		license.addFeature("ResidueDegree", ResidueDegree);
//		return license;
//	}
//
//	public LicenseEntity(License license) {
//		this.Customer = String.valueOf(license.getFeature("Customer"));
//		this.Company = String.valueOf(license.getFeature("Company"));
//		this.SystemName = String.valueOf(license.getFeature("SystemName"));
////		this.MajorVersion = String.valueOf(license.getFeature("MajorVersion"));
////		this.MinorVersion = String.valueOf(license.getFeature("MinorVersion"));
//		this.CreateTime = String.valueOf(license.getFeature("CreateTime"));
//		this.RegistrationState = String.valueOf(license.getFeature("RegistrationState"));
//		this.MacAddress = String.valueOf(license.getFeature("MacAddress"));
//		this.AuthorName = String.valueOf(license.getFeature("AuthorName"));
//		this.email = String.valueOf(license.getFeature("email"));
//		this.ExpiredDate = String.valueOf(license.getFeature("ExpiredDate"));
//		this.LastAccessTime = String.valueOf(license.getFeature("LastAccessTime"));
//		this.ResidueDegree = String.valueOf(license.getFeature("ResidueDegree"));
//	}
//	public String getExpiredDate() {
//		return ExpiredDate;
//	}
//	public void setExpiredDate(String expiredDate) {
//		ExpiredDate = expiredDate;
//	}
//	public String getCreateTime() {
//		return CreateTime;
//	}
//	public void setCreateTime(String createTime) {
//		CreateTime = createTime;
//	}
//	public String getLastAccessTime() {
//		return LastAccessTime;
//	}
//	public void setLastAccessTime(String lastAccessTime) {
//		LastAccessTime = lastAccessTime;
//	}
//	public String getSystemName() {
//		return SystemName;
//	}
//	public void setSystemName(String systemName) {
//		SystemName = systemName;
//	}
//	public String getMacAddress() {
//		return MacAddress;
//	}
//	public void setMacAddress(String macAddress) {
//		MacAddress = macAddress;
//	}
//	public String getRegistrationState() {
//		return RegistrationState;
//	}
//	public void setRegistrationState(String registrationState) {
//		RegistrationState = registrationState;
//	}
//	public String getCustomer() {
//		return Customer;
//	}
//	public void setCustomer(String customer) {
//		Customer = customer;
//	}
//	public String getCompany() {
//		return Company;
//	}
//	public void setCompany(String company) {
//		Company = company;
//	}
//	public String getMajorVersion() {
//		return MajorVersion;
//	}
//	public void setMajorVersion(String majorVersion) {
//		MajorVersion = majorVersion;
//	}
//	public String getResidueDegree() {
//		return ResidueDegree;
//	}
//	public void setResidueDegree(String residueDegree) {
//		ResidueDegree = residueDegree;
//	}
//	public String getMinorVersion() {
//		return MinorVersion;
//	}
//	public void setMinorVersion(String minorVersion) {
//		MinorVersion = minorVersion;
//	}
//	public String getAuthorName() {
//		return AuthorName;
//	}
//	public void setAuthorName(String authorName) {
//		AuthorName = authorName;
//	}
//	public String getEmail() {
//		return email;
//	}
//	public void setEmail(String email) {
//		this.email = email;
//	}
//
//	public void toEntity() throws Exception{
//		BaseUtils.showObj(this);
//	}
//}
