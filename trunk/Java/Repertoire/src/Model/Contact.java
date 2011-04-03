package Model;

import java.util.Date;

public class Contact
{
	private Date 	_birthDay;
	private String 	_firstName;
	private String 	_lastName;
	private String 	_emailAdress;
	private int 	_homePhoneNumber;
	private int 	_mobileNumber;
	
	private String _adress;
	private String _city;
	private int    _zipCode;
	
	public Contact(String firstName,
				   String lastName,
				   Date birthDay,
				   String adress,
				   int zipCode,
				   String city,
				   int 	homePhoneNumber,
				   int 	mobileNumber,
				   String 	emailAdress)				   
	{
		_birthDay=birthDay;
		_firstName=firstName;
		_lastName=lastName;
		_emailAdress=emailAdress;
		_homePhoneNumber=homePhoneNumber;
		_mobileNumber=mobileNumber;
		_adress=adress;
		_city=city;
		_zipCode=zipCode;
	}

	
	
	
	
	/**
	 * @param _birthDay the _birthDay to set
	 */
	public void setBirthDay(Date _birthDay) {
		this._birthDay = _birthDay;
	}

	/**
	 * @return the _birthDay
	 */
	public Date getBirthDay() {
		return _birthDay;
	}

	/**
	 * @param _firstName the _firstName to set
	 */
	public void setFirstName(String _firstName) {
		this._firstName = _firstName;
	}

	/**
	 * @return the _firstName
	 */
	public String getFirstName() {
		return _firstName;
	}

	/**
	 * @param _lastName the _lastName to set
	 */
	public void setLastName(String _lastName) {
		this._lastName = _lastName;
	}

	/**
	 * @return the _lastName
	 */
	public String getLastName() {
		return _lastName;
	}

	/**
	 * @param _emailAdress the _emailAdress to set
	 */
	public void setEmailAdress(String _emailAdress) {
		this._emailAdress = _emailAdress;
	}

	/**
	 * @return the _emailAdress
	 */
	public String getEmailAdress() {
		return _emailAdress;
	}

	/**
	 * @param _homePhoneNumber the _homePhoneNumber to set
	 */
	public void setHomePhoneNumber(int _homePhoneNumber) {
		this._homePhoneNumber = _homePhoneNumber;
	}

	/**
	 * @return the _homePhoneNumber
	 */
	public int getHomePhoneNumber() {
		return _homePhoneNumber;
	}

	/**
	 * @param _mobileNumber the _mobileNumber to set
	 */
	public void setMobileNumber(int _mobileNumber) {
		this._mobileNumber = _mobileNumber;
	}

	/**
	 * @return the _mobileNumber
	 */
	public int getMobileNumber() {
		return _mobileNumber;
	}

	/**
	 * @param _adress the _adress to set
	 */
	public void setAdress(String _adress) {
		this._adress = _adress;
	}

	/**
	 * @return the _adress
	 */
	public String getAdress() {
		return _adress;
	}

	/**
	 * @param _city the _city to set
	 */
	public void setCity(String _city) {
		this._city = _city;
	}

	/**
	 * @return the _city
	 */
	public String getCity() {
		return _city;
	}

	/**
	 * @param _zipCode the _zipCode to set
	 */
	public void setZipCode(int _zipCode) {
		this._zipCode = _zipCode;
	}

	/**
	 * @return the _zipCode
	 */
	public int getZipCode() {
		return _zipCode;
	}
	
	
}
