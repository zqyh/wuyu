package com.whackode.itrip.pojo.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <b>爱旅行-酒店房间信息实体对象</b>
 * @author Arthur
 * @version 1.0.0
 * @since 1.0.0
 */
public class HotelRoom implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long hotelId;
	private String roomTitle;
	private BigDecimal roomPrice;
	private Long roomBedTypeId;
	private Integer isHavingBreakfast;
	private Integer payType;
	private Double satisfaction;
	private Integer isBook;
	private Integer isCancel;
	private Integer isTimelyResponse;
	private Date creationDate;
	private Long createdBy;
	private Date modifyDate;
	private Long modifiedBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getHotelId() {
		return hotelId;
	}

	public void setHotelId(Long hotelId) {
		this.hotelId = hotelId;
	}

	public String getRoomTitle() {
		return roomTitle;
	}

	public void setRoomTitle(String roomTitle) {
		this.roomTitle = roomTitle;
	}

	public BigDecimal getRoomPrice() {
		return roomPrice;
	}

	public void setRoomPrice(BigDecimal roomPrice) {
		this.roomPrice = roomPrice;
	}

	public Long getRoomBedTypeId() {
		return roomBedTypeId;
	}

	public void setRoomBedTypeId(Long roomBedTypeId) {
		this.roomBedTypeId = roomBedTypeId;
	}

	public Integer getIsHavingBreakfast() {
		return isHavingBreakfast;
	}

	public void setIsHavingBreakfast(Integer isHavingBreakfast) {
		this.isHavingBreakfast = isHavingBreakfast;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public Double getSatisfaction() {
		return satisfaction;
	}

	public void setSatisfaction(Double satisfaction) {
		this.satisfaction = satisfaction;
	}

	public Integer getIsBook() {
		return isBook;
	}

	public void setIsBook(Integer isBook) {
		this.isBook = isBook;
	}

	public Integer getIsCancel() {
		return isCancel;
	}

	public void setIsCancel(Integer isCancel) {
		this.isCancel = isCancel;
	}

	public Integer getIsTimelyResponse() {
		return isTimelyResponse;
	}

	public void setIsTimelyResponse(Integer isTimelyResponse) {
		this.isTimelyResponse = isTimelyResponse;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
}
