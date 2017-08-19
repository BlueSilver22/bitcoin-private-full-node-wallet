/************************************************************************************************
 *   ____________ _   _  _____          _      _____ _    _ _______          __   _ _      _   
 *  |___  /  ____| \ | |/ ____|        | |    / ____| |  | |_   _\ \        / /  | | |    | |  
 *     / /| |__  |  \| | |     __ _ ___| |__ | |  __| |  | | | |  \ \  /\  / /_ _| | | ___| |_ 
 *    / / |  __| | . ` | |    / _` / __| '_ \| | |_ | |  | | | |   \ \/  \/ / _` | | |/ _ \ __|
 *   / /__| |____| |\  | |___| (_| \__ \ | | | |__| | |__| |_| |_   \  /\  / (_| | | |  __/ |_ 
 *  /_____|______|_| \_|\_____\__,_|___/_| |_|\_____|\____/|_____|   \/  \/ \__,_|_|_|\___|\__|
 *                                                                                             
 * Copyright (c) 2017 Ivan Vaklinov <ivan@vaklinov.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 **********************************************************************************/
package com.vaklinov.zcashui.msg;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.WriterConfig;
import com.vaklinov.zcashui.Util;


/**
 * Encapsulates a messaging identity.
 *
 * @author Ivan Vaklinov <ivan@vaklinov.com>
 */
public class MessagingIdentity
{
	private String nickname;
	private String sendreceiveaddress;
	private String senderidaddress;
	private String firstname;
	private String middlename;
	private String surname;
	private String email;
	private String streetaddress;
	private String facebook;
	private String twitter;
	
	// TODO: automatically cut fields to XXX length to avoid issues with accidental big data
	
	public MessagingIdentity()
	{
		// Empty
	}
	
	
	public MessagingIdentity(JsonObject obj)
		 throws IOException
	{
		this.copyFromJSONObject(obj);
	}
	
	
	public MessagingIdentity(File f)
		throws IOException
	{
		Reader r = null;
			
		try
		{
			// TODO: Repackage ParseException as IOEx!!! - 
			
			r = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
			JsonObject obj = Json.parse(r).asObject();
			
			this.copyFromJSONObject(obj);
		} finally
		{
			if (r != null)
			{
				r.close();
			}
		}
	}


	public void copyFromJSONObject(JsonObject obj)
		throws IOException
	{
		// Mandatory fields!
		this.nickname           = obj.getString("nickname",           "");
		this.sendreceiveaddress = obj.getString("sendreceiveaddress", "");
		this.senderidaddress    = obj.getString("senderidaddress",    "");
	
		this.firstname          = obj.getString("firstname",          "");
		this.middlename         = obj.getString("middlename",         "");
		this.surname            = obj.getString("surname",            "");
		this.email              = obj.getString("email",              "");
		this.streetaddress      = obj.getString("streetaddress",      "");
		this.facebook           = obj.getString("facebook",           "");		
		this.twitter            = obj.getString("twitter",            "");		
		
		// Make sure the mandatory fields are there
		if (Util.stringIsEmpty(this.nickname) || Util.stringIsEmpty(this.senderidaddress))
		{
			throw new IOException("Mandatory field is missing in creating messaging identity!");
		}
	}
	
	
	public JsonObject toJSONObject()
	{
		JsonObject obj = new JsonObject();
		
		obj.set("nickname",           nickname);
		obj.set("sendreceiveaddress", sendreceiveaddress);
		obj.set("senderidaddress",    senderidaddress);
		obj.set("firstname",          firstname);
		obj.set("middlename",         middlename);
		obj.set("surname",            surname);
		obj.set("email",              email);
		obj.set("streetaddress",      streetaddress);
		obj.set("facebook",           facebook);		
		obj.set("twitter",            twitter);
		
		return obj;
	}
	
	
	public void writeToFile(File f)
		throws IOException
	{
		Writer w = null;
		
		try
		{
			w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "UTF-8"));
			w.write(this.toJSONObject().toString(WriterConfig.PRETTY_PRINT));
		} finally
		{
			if (w != null)
			{
				w.close();
			}
		}
	}
		
	
	public String getNickname() 
	{
		return nickname;
	}
	
	public void setNickname(String nickname) 
	{
		this.nickname = nickname;
	}
	
	public String getSendreceiveaddress() 
	{
		return sendreceiveaddress;
	}
	
	public void setSendreceiveaddress(String sendreceiveaddress) 
	{
		this.sendreceiveaddress = sendreceiveaddress;
	}
	
	public String getSenderidaddress() 
	{
		return senderidaddress;
	}
	
	public void setSenderidaddress(String senderidaddress) 
	{
		this.senderidaddress = senderidaddress;
	}
	
	public String getFirstname() 
	{
		return firstname;
	}
	
	public void setFirstname(String firstname) 
	{
		this.firstname = firstname;
	}
	
	public String getMiddlename() 
	{
		return middlename;
	}
	
	public void setMiddlename(String middlename) 
	{
		this.middlename = middlename;
	}
	
	public String getSurname() 
	{
		return surname;
	}
	
	public void setSurname(String surname) 
	{
		this.surname = surname;
	}
	
	public String getEmail() 
	{
		return email;
	}
	
	public void setEmail(String email) 
	{
		this.email = email;
	}
	
	public String getStreetaddress() 
	{
		return streetaddress;
	}
	
	public void setStreetaddress(String streetaddress) 
	{
		this.streetaddress = streetaddress;
	}
	
	public String getFacebook() 
	{
		return facebook;
	}
	
	public void setFacebook(String facebook) 
	{
		this.facebook = facebook;
	}
	
	public String getTwitter() 
	{
		return twitter;
	}
	
	public void setTwitter(String twitter) 
	{
		this.twitter = twitter;
	}
	
	
	/**
	 * Produces a string in the form nick (first middle sur) suitable for display purposes.
	 * 
	 * @return a string in the form nick (first middle sur) suitable for display purposes.
	 */
	public String getDiplayString()
	{
		
		MessagingIdentity id = this;
		String contactString = id.getNickname();
		
		// TODO: avoid space if no surname - check for empty strng too
		if ((!Util.stringIsEmpty(id.getFirstname())) || (!Util.stringIsEmpty(id.getMiddlename())) || 
			(!Util.stringIsEmpty(id.getSurname())))
		{
			contactString += " (";
			contactString += (id.getFirstname() != null)  ? (id.getFirstname() + " ")  : "";
			contactString += (id.getMiddlename() != null) ? (id.getMiddlename() + " ") : "";
			contactString += (id.getSurname() != null)    ? (id.getSurname())          : "";
			contactString += ")";
		}
		
		return contactString;
	}
	
	
	/**
	 * Performs a comparison of whether one messaging identity object is identical to another.
	 * Currently the criterion is that the two addresses used must be the same!
	 * 
	 * @param other other messaging identity to compare to
	 * 
	 * @return true if identical;
	 */
	public boolean isIdenticalTo(MessagingIdentity other)
	{
		return this.senderidaddress.equals(other.senderidaddress) &&
			   this.sendreceiveaddress.equals(other.sendreceiveaddress);
	}
}