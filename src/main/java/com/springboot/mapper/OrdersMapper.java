package com.springboot.mapper;

import com.springboot.entity.Orders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 文涛
 * @since 2023-03-04
 */
public interface OrdersMapper extends BaseMapper<Orders> {

    @Update("update orders set status = #{status}, payment_time = #{paymentTime}, alipay_no = #{alipayNo} where orderno = #{orderno}")
    int updateState(@Param("orderno") String orderno, @Param("status")String status,
                     @Param("paymentTime")String gmtPayment, @Param("alipayNo")String alipayTradeNo);
}
