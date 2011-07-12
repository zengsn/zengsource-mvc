/**
 * 
 */
package org.zengsource.demo.mvc.model;

import java.util.Date;

import org.zengsource.util.IDUtil;

/**
 * @author zsn
 * 
 */
public class Message implements Comparable<Message> {

	private String id;
	private String content;
	private User author;
	private Integer comments = 0;
	private Integer forwards = 0;
	private Date lastPost;

	public Message() {
		id = IDUtil.generate();
		lastPost = new Date();
	}

	public int compareTo(Message o) {
		return this.lastPost.getTime() - o.getLastPost().getTime() >= 0 ? -1 : 1;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Integer getComments() {
		return comments;
	}

	public void setComments(Integer comments) {
		this.comments = comments;
	}

	public Integer getForwards() {
		return forwards;
	}

	public void setForwards(Integer forwards) {
		this.forwards = forwards;
	}

	public Date getLastPost() {
		return lastPost;
	}

	public void setLastPost(Date lastPost) {
		this.lastPost = lastPost;
	}

}
