package com.priyam;

import java.util.ArrayList;
import java.util.Date;

public class Post implements Comparable<Object>{
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	private int id;
	private String email;
	private int likes;
	private String message;
	private Date date;
	private long time;
	private ArrayList<String> likers;

	public ArrayList<String> getLikers() {
		return likers;
	}
	public void setLikers(ArrayList<String> likers) {
		this.likers = likers;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	private boolean active;
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		Post p=(Post)o;
		Date d=p.getDate();
		if(date.after(d)){
			return -1;
		}
		else if(date.before(d)){
			return 1;
		}
		else{
			long t=p.getTime();
			if(time>t){
				return -1;
			}
			else if(time<t){
				return 1;
			}
			else return 0;
		}
	}
}
