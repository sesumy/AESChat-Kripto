package com.sesumy.aeschat_kripto.model;


import com.sesumy.aeschat_kripto.UserList;

import java.util.Date;

/**
 * The Class Conversation is a Java Bean class that represents a single chat
 * conversation message.
 */
public class Conversation
{


	/** The Constant STATUS_SENDING. */
	public static final int STATUS_SENDING = 0;

	/** The Constant STATUS_SENT. */
	public static final int STATUS_SENT = 1;

	/** The Constant STATUS_FAILED. */
	public static final int STATUS_FAILED = 2;

	/** The msg. */
	private String msg;
	private int status = STATUS_SENT;
	private Date date;
	private String sender;
    private String receiver;
    private String photoUrl;


	public Conversation(){
	}
	/**
	 * Instantiates a new conversation.
	 *
	 * @param msg
	 *            the msg
	 * @param date
	 *            the date
	 * @param sender
	 *            the sender
     * @param receiver
     *            the receiver
	**/

	public Conversation(String msg, Date date, String sender, String receiver) {
        this.msg = msg;
		this.date = date;
		this.sender = sender;
        this.receiver = receiver;
    //    this.photoUrl = photoUrl;, String photoUrl
	}

	/**
	 * Gets the msg.
	 * 
	 * @return the msg
	 */
	public String getMsg()
	{
		return msg;
	}

	/**
	 * Sets the msg.
	 *
	 * @param msg
	 *            the new msg
	 */
	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	/**
	 * Checks if is sent.
	 * 
	 * @return true, if is sent
	 */
	public boolean isSent()

	{
		return UserList.user.getId().contentEquals(sender);
	}

	/**
	 * Gets the date.
	 * 
	 * @return the date
	 */
	public Date getDate() {

        return date;
	}

	/**
	 * Sets the date.
	 *
	 * @param date
	 *            the new date
	 */
	public void setDate(Date date)
	{
		this.date = date;
	}

    /**
     * Gets the sender.
     *
     * @return the sender
     */
    public String getReceiver()
    {
        return receiver;
    }

    /**
     * Sets the sender.
     *
     * @param receiver
     *            the new sender
     */
    public void setReceiver(String receiver)
    {
        this.receiver = receiver;
    }

	/**
	 * Gets the sender.
	 * 
	 * @return the sender
	 */
	public String getSender()
	{
		return sender;
	}

	/**
	 * Sets the sender.
	 *
	 * @param sender
	 *            the new sender
	 */
	public void setSender(String sender)
	{
		this.sender = sender;
	}

	/**
	 * Gets the status.
	 * 
	 * @return the status
	 */
	public int getStatus()
	{
		return status;
	}

	/**
	 * Sets the status.
	 * 
	 * @param status
	 *            the new status
	 */
	public void setStatus(int status)
	{
		this.status = status;
	}

    /**
     * Set the photo URL
     *
     * @param photoUrl
     */
   // public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }

    /**
     * Get the photo URL
     *
     * @return photoUrl
     */
   // public String getPhotoUrl() { return this.photoUrl; }


}
