package rkrk.whyprice.member.application.service.exception

import org.springframework.dao.InvalidDataAccessApiUsageException

class NotExistsMemberException(
    val msg: String,
) : InvalidDataAccessApiUsageException(msg)
