package com.ssafy.kkoma.api.login.validator;

import com.ssafy.kkoma.domain.member.constant.MemberType;
import com.ssafy.kkoma.global.error.ErrorCode;
import com.ssafy.kkoma.global.error.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class OauthValidator {

    public void validateMemberType(String memberType) {
        if (!MemberType.isMemberType(memberType)) {
            throw new BusinessException(ErrorCode.INVALID_MEMBER_TYPE);
        }
    }

}
