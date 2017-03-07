package model

import java.security.MessageDigest
import com.sun.istack.internal.logging.Logger

/**
 * Scala utilities object
 */
object AuthenticationHandlerUtilitiesScala {
	val logger : Logger = Logger.getLogger(getClass)

	/**
	 * receive user password and encrypt it
	 * returns md5 string
	 * @param pass - string contain user password
	 * @return - hashed password
	 */
	def passEncryption(pass:String): String = {
			logger.info("Encrypt password")
			var bytes :Array[Byte]  = null
			// Create MessageDigest instance for MD5
			val md = java.security.MessageDigest.getInstance("MD5")
			//Add password bytes to digest
			md.update(pass.getBytes())
			//Get the hash's bytes
			bytes = md.digest()
			//This bytes[] has bytes in decimal format, Convert it to hexadecimal format
			val sb = new StringBuilder()
			var i = 0
			for(i <-bytes.indices){
				sb.append(Integer.toString((bytes(i) & 0xff) + 0x100, 16).substring(1))
			}
			//Get complete hashed password in hex format
			return sb.toString()
	}
}