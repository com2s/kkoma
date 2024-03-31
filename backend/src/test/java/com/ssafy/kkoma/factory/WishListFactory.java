package com.ssafy.kkoma.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ssafy.kkoma.domain.member.entity.Member;
import com.ssafy.kkoma.domain.product.entity.Product;
import com.ssafy.kkoma.domain.product.entity.WishList;
import com.ssafy.kkoma.domain.product.repository.WishListRepository;

@Component
public class WishListFactory {

	@Autowired
	private WishListRepository wishListRepository;

	public WishList createWishList(Member member, Product product) {
		WishList wishList = new WishList();
		wishList.setMemberAndProduct(member, product);
		return wishListRepository.save(wishList);
	}

}
