package com.gmail.hexragon.gn4rBot.util;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.NumberFormat;

public class SystemInfo {
	
	private final Runtime runtime = Runtime.getRuntime();
	
	public String info() {
		return this.osInfo() +
				this.memInfo() +
				this.diskInfo();
	}
	
	public String osName() {
		return System.getProperty("os.name");
	}
	
	public String osVersion() {
		return System.getProperty("os.version");
	}
	
	public String osArch() {
		return System.getProperty("os.arch");
	}
	
	public long totalMem() {
		return Runtime.getRuntime().totalMemory();
	}
	
	public long usedMem() {
		return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
	}
	
	public String memInfo() {
		NumberFormat format = NumberFormat.getInstance();
		StringBuilder sb = new StringBuilder();
		long maxMemory = runtime.maxMemory();
		long allocatedMemory = runtime.totalMemory();
		long freeMemory = runtime.freeMemory();
		sb.append("Free memory: ");
		sb.append(format.format(freeMemory / 1024));
		sb.append("\n");
		sb.append("Allocated memory: ");
		sb.append(format.format(allocatedMemory / 1024));
		sb.append("\n");
		sb.append("Max memory: ");
		sb.append(format.format(maxMemory / 1024));
		sb.append("\n");
		sb.append("Total free memory: ");
		sb.append(format.format((freeMemory + (maxMemory - allocatedMemory)) / 1024));
		sb.append("\n");
		return sb.toString();
		
	}
	
	public String osInfo() {
		return  "OS: " + this.osName() + "\n" +
				"Version: " + this.osVersion() + "\n" +
				"Arch: " + this.osArch() + "\n" +
				"Available processors (cores): " + runtime.availableProcessors() + "\n";
	}
	
	public String diskInfo() {
        /* Get a list of all filesystem roots on this system */
		File[] roots = File.listRoots();
		StringBuilder sb = new StringBuilder();

        /* For each filesystem root, print some info */
		for (File root : roots) {
			sb.append("File system root: ");
			sb.append(root.getAbsolutePath());
			sb.append("\n");
			sb.append("Total space (bytes): ");
			sb.append(root.getTotalSpace());
			sb.append("\n");
			sb.append("Free space (bytes): ");
			sb.append(root.getFreeSpace());
			sb.append("\n");
			sb.append("Usable space (bytes): ");
			sb.append(root.getUsableSpace());
			sb.append("\n");
		}
		return sb.toString();
	}
	
	public void printUsage() {
		OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
		for (Method method : operatingSystemMXBean.getClass().getDeclaredMethods()) {
			method.setAccessible(true);
			if (method.getName().startsWith("get")
					&& Modifier.isPublic(method.getModifiers())) {
				Object value;
				try {
					value = method.invoke(operatingSystemMXBean);
				} catch (Exception e) {
					value = e;
				} // try
				System.out.println(method.getName() + " = " + value);
			} // if
		} // for
	}
}