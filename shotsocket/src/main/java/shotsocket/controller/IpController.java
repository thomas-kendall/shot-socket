package shotsocket.controller;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import shotsocket.model.IpInfo;

@RestController
@RequestMapping("/ipinfo")
public class IpController {

	private InetAddress getInetAddress() throws SocketException {
		for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
			NetworkInterface intf = en.nextElement();
			for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
				InetAddress inetAddress = enumIpAddr.nextElement();
				if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
					return inetAddress;
				}
			}
		}

		return null;
	}

	@RequestMapping(method = RequestMethod.GET)
	public IpInfo getIpInfo() throws SocketException {
		InetAddress address = getInetAddress();

		IpInfo info = new IpInfo();
		info.setIpAddress(address.getHostAddress());

		return info;
	}
}
