import be.derycke.pieter.com.COMException;
import be.derycke.pieter.com.Guid;
import java.io.*;
import java.math.BigInteger;

import jmtp.*;
import java.util.logging.Logger;

public class Jmtp {

	static Logger logger = Logger.getLogger(Jmtp.class.getName());
	static final String javaPathKey = "java.library.path";

	public static void main(String[] args) {
		logger.info(System.getProperty(javaPathKey));

		 // printAllDevices();

		PortableDevice device = getDeviceByModel("E5823");

		if (device != null) {

			PortableDeviceStorageObject storage = getStorageByName(device, "SD Card");

			if (storage != null) {
				PortableDeviceObject topLevelObjectInStorage = getTopLevelObjectInStorageByOriginalFileName(storage,
						"Music_test");

				if (topLevelObjectInStorage instanceof PortableDeviceFolderObject) {
					PortableDeviceFolderObject folder = (PortableDeviceFolderObject)topLevelObjectInStorage;

					String filePathInDisk ="D:\\temp\\itunes2Android__tests\\music\\0_song_1_OUI.mp3";
					copyFileFromDiskToDevice(filePathInDisk, folder);
					
				} else {

				}

			}

			device.close();
		}
	}

	protected static boolean copyFileFromDiskToDevice(String filePathInDisk,
			PortableDeviceFolderObject folder) {

		BigInteger bigInteger1 = new BigInteger("123456789");
		File fileInDisk = new File(filePathInDisk);
		if (!fileInDisk.exists()) {
			logger.warning("Cannot find file:" + filePathInDisk + ". Will not be copied");
			return false;
		}
		try {
			String arg1 = "artist";
			String arg2 = "title";
			
			folder.addAudioObject(fileInDisk, arg1, arg2, bigInteger1);
			logger.info("File:" + filePathInDisk + " copied with success");
		} catch (Exception e) {
			logger.severe("Exception e = " + e);
		}

		return false;
	}

	protected static void updateJavaPath() {
		String initialJavaPath = System.getProperty(javaPathKey);
		String javaPath = "D:\\temp\\yanndanielou-programmation\\Java\\Jmtp\\JMTP" + ";" + initialJavaPath;
		System.setProperty(javaPathKey, javaPath);
	}

	private static void printAllDevices() {
		PortableDeviceManager manager = new PortableDeviceManager();
		PortableDevice[] devices = manager.getDevices();
		logger.info(devices.length + " devices found");
		for (PortableDevice device : devices) {

			// Connect to my mp3-player
			device.open();

			printInfosOfOpenDevice(device);
			printDeviceContents(device);

			device.close();
		}
	}

	private static PortableDevice getDeviceByModel(String model) {
		PortableDeviceManager manager = new PortableDeviceManager();
		PortableDevice[] devices = manager.getDevices();
		logger.info(devices.length + " devices found");
		for (PortableDevice device : devices) {

			// Connect to my mp3-player
			device.open();

			String deviceModel = device.getModel();

			if (model.equals(deviceModel)) {
				logger.info("Device with model:[" + model + "] found");
				return device;
			}
			device.close();
		}
		logger.warning("Cannot find device with model:[" + model + "]");
		return null;
	}

	private static void printDeviceContents(PortableDevice device) {
		String deviceModel = device.getModel();
		logger.info("printDeviceContents: Scanning device " + deviceModel + "...");

		// Iterate over deviceObjects
		PortableDeviceObject[] portableDeviceObjects = device.getRootObjects();

		logger.info("printDeviceContents: There are " + portableDeviceObjects.length + " portable device objects");

		for (PortableDeviceObject portableDeviceObject : portableDeviceObjects) {

			// If the object is a storage object
			if (portableDeviceObject instanceof PortableDeviceStorageObject) {
				PortableDeviceStorageObject storage = (PortableDeviceStorageObject) portableDeviceObject;
				printStorageContents(storage, 0);
			} else {
				logger.info("Portable device object is not a PortableDeviceStorageObject but is a "
						+ portableDeviceObject.getClass().getSimpleName() + ". Will be ignored");
			}
		}

	}

	private static PortableDeviceObject getTopLevelObjectInStorageByOriginalFileName(
			PortableDeviceStorageObject storage, String originalFileName) {
		PortableDeviceObject[] topLevelObjectsInStorage = storage.getChildObjects();

		for (PortableDeviceObject topLevelObjectInStorage : topLevelObjectsInStorage) {
			if (originalFileName.equals(topLevelObjectInStorage.getOriginalFileName())) {
				logger.info("Top level object  with original file name:[" + originalFileName + "] found in storage");
				return topLevelObjectInStorage;
			}
		}

		logger.warning("getTopLevelObjectInStorageByName: Cannot find top level object with original file name:["
				+ originalFileName + "] in storage");
		return null;

	}

	private static void printStorageContents(PortableDeviceStorageObject storage, int depth) {
		PortableDeviceObject[] topLevelObjectsInStorage = storage.getChildObjects();
		String storageDescription = storage.getDescription();
		String storageFileSystemType = storage.getFileSystemType();
		String storageName = storage.getName();
		logger.info(prefix(depth) + "printDeviceContents: Storage found. Name:[" + storageName + "], description:["
				+ storageDescription + "] in file system type:[" + storageFileSystemType + "] and has "
				+ topLevelObjectsInStorage.length + " children");
		for (PortableDeviceObject children : topLevelObjectsInStorage) {
			printPortableDeviceObject(children, depth+1);
		}
	}

	private static void printPortableDeviceObject(PortableDeviceObject portableDeviceObject, int depth) {
		if (portableDeviceObject instanceof PortableDeviceStorageObject) {
			PortableDeviceStorageObject storage = (PortableDeviceStorageObject) portableDeviceObject;
			printStorageContents(storage, depth+1);
		} else if (portableDeviceObject instanceof PortableDeviceFolderObject) {
			PortableDeviceFolderObject portableDeviceFolderObject = (PortableDeviceFolderObject) portableDeviceObject;
			printFolderContents(portableDeviceFolderObject, depth+1);
		} else {
			logger.info(prefix(depth) + "Child:" + portableDeviceObject.getOriginalFileName() + " with class "
					+ portableDeviceObject.getClass().getName());
		}
	}

	private static String prefix(int depth) {
		String prefix = "";

		while (depth > 0) {
			prefix = prefix + "  ";
			depth--;
		}

		return prefix;
	}

	private static void printFolderContents(PortableDeviceFolderObject portableDeviceFolderObject, int depth) {
		PortableDeviceObject[] children = portableDeviceFolderObject.getChildObjects();
		logger.info(prefix(depth) + "Folder:" + portableDeviceFolderObject.getOriginalFileName() + ". And has "
				+ children.length + " children");

		for (PortableDeviceObject child : children) {
			printPortableDeviceObject(child, depth+1);
		}
	}

	private static PortableDeviceStorageObject getStorageByName(PortableDevice device, String name) {

		String deviceModel = device.getModel();

		logger.info("getStorageByName: Scanning device " + deviceModel + "...");

		// Iterate over deviceObjects
		PortableDeviceObject[] portableDeviceObjects = device.getRootObjects();

		logger.info("getStorageByName: There are " + portableDeviceObjects.length + " portable device objects");

		for (PortableDeviceObject portableDeviceObject : portableDeviceObjects) {

			// If the object is a storage object
			if (portableDeviceObject instanceof PortableDeviceStorageObject) {
				PortableDeviceStorageObject storage = (PortableDeviceStorageObject) portableDeviceObject;
				PortableDeviceObject[] topLevelObjectsInStorage = storage.getChildObjects();

				String storageDescription = storage.getDescription();
				String storageFileSystemType = storage.getFileSystemType();
				String storageName = storage.getName();

				logger.info("getStorageByName: Storage found. Name:[" + storageName + "], description:["
						+ storageDescription + "] in file system type:[" + storageFileSystemType + "] and has "
						+ topLevelObjectsInStorage.length + " children");

				if (name.equals(storageName)) {
					logger.info("getStorageByName: Storage with name:[" + name + "] found");
					return storage;
				}

			}
		}

		logger.warning("getStorageByName: Cannot find storage with name:[" + name + "]");
		return null;
	}

	private static void printInfosOfOpenDevice(PortableDevice device) {
		String deviceDescription = device.getDescription();
		String deviceModel = device.getModel();
		String deviceManufacturer = device.getManufacturer();
		String deviceProtocole = device.getProtocol();
		String deviceFirmwareVersion = device.getFirmwareVersion();
		String deviceFriendlyName = device.getFriendlyName();
		int devicePowerLevel = device.getPowerLevel();
		PowerSource devicePowerSource = device.getPowerSource();

		logger.info("Device: \n deviceDescription:" + deviceDescription + "\n deviceModel:" + deviceModel
				+ "\n deviceManufacturer:" + deviceManufacturer + "\n deviceProtocole:" + deviceProtocole
				+ "\n deviceFirmwareVersion:" + deviceFirmwareVersion + "\n deviceFriendlyName:" + deviceFriendlyName
				+ "\n devicePowerLevel:" + devicePowerLevel + "\n devicePowerSource:" + devicePowerSource + "\n");
	}
}
