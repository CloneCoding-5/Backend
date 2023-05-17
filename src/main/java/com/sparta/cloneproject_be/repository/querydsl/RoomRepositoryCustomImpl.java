package com.sparta.cloneproject_be.repository.querydsl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.cloneproject_be.entity.QRoom;
import com.sparta.cloneproject_be.entity.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Iterator;
import java.util.List;

public class RoomRepositoryCustomImpl implements RoomRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Autowired
    public RoomRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    //여러 조건에 따라 Room 객체 검색. paginated 결과 반환
    @Override
    public Page<Room> findRooms(int minPrice, int maxPrice, String region, List<String> amenities, String roomType, String categories, Pageable pageable) {
        QRoom room = QRoom.room;

        BooleanBuilder builder = new BooleanBuilder();
        //조건에 따라 동적 쿼리 구성

        builder.and(room.price.goe(minPrice));  // greater or equal
        builder.and(room.price.loe(maxPrice));  //less or equal

        if (region != null) {
            builder.and(room.region.eq(region));
        }
        if (roomType != null) {
            builder.and(room.roomType.eq(roomType));
        }
        //편의시설 필터링
        if (amenities != null && !amenities.isEmpty()) {
            for (String amenity : amenities) {
                builder.and(room.roomAmenities.contains(amenity));
            }
        }

        //카테고리
        if (categories != null) {
            builder.and(room.categories.contains(categories));
        }

        //실제 쿼리 생성
        //queryFactory.selectFrom -> room 테이블에서 데이터 선택,
        //where -> BooleanBuilder 사용, 결과는 roomid 내림차순 정렬
        JPQLQuery<Room> query = queryFactory
                .selectFrom(room)
                .where(builder)
                .orderBy(room.roomId.desc());

        //아래 정의한 메서드 호출 pageable 객체에 따라 페이징
        JPQLQuery<Room> finalQuery = applyPagination(pageable, query);

        List<Room> content = finalQuery.fetch();  // DB에서 쿼리에 해당하는 Room 객체 가져옴. List<Room>으로 결과 반환.
        long total = finalQuery.fetchCount();
        //쿼리로 반환되는 Room 객체의 수 반환.
        //페이지네이션 구현 시 필요. 페이징 결과가 아닌 총 결과 수를 알려주므로 전체 페이지 수 계산

        return new PageImpl<>(content, pageable, total);
        //Page 인터페이스 구현체 PageImpl 객체 생성.
        // 페이지네이션된 결과를 담고 클라에 리턴.
        // content(페이지에 표시될 Room 객체들의 리스트), pageable(페이징 정보를 담고 있는 Pageable 객체), total(총 Room 객체의 수).
    }

    //pageable 객체 사용. 쿼리에 페이징과 정렬을 적용
    private JPQLQuery<Room> applyPagination(Pageable pageable, JPQLQuery<Room> query) {
        //offset, pageSize -> 쿼리의 offset과 limit 설정
        if (pageable.isPaged()) {//객체가 페이징 정보 가지고 있을때 정보를 쿼리에 적용
            query.offset(pageable.getOffset()); //쿼리가 결과의 어디부터 가져올지 설정
            query.limit(pageable.getPageSize()); //한 페이지당 결과 몇개 가져올지 설정
        }
        //pageable 객체의 sort 필드를 사용. 쿼리의 정렬 조건을 설정
        if (pageable.getSort().isSorted()) {  //정렬 정보를 가지고 있는지 확인 -> 있으면 쿼리에 적용
            Iterator<Sort.Order> iterator = pageable.getSort().iterator();
            //Pageable 객체에서 Sort 객체(정렬 규칙(Sort.Order 객체) 포함)를 가져와 이터레이터 생성
            while (iterator.hasNext()) {  //Sort 객체가 가진 정렬 규칙을 순회
                Sort.Order order = iterator.next();
                PathBuilder<Room> pathBuilder = new PathBuilder<>(Room.class, "room");
                query.orderBy(new OrderSpecifier(order.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(order.getProperty())));
            }//PathBuilder(동적 쿼리에서 경로 표현식을 생성)객체 사용-> JPQL 쿼리에 정렬 조건을 추가. Room 속성을 대상으로 하는 경로 표현식을 생성
            //JPQL 쿼리에 정렬 조건을 추가. OrderSpecifier 객체로 정렬 방향과 대상속성 지정. 정렬 방향은 Sort.Order 객체에서 가져옴
            //order가 오름차순이면 Order.ASC, 내림차순 Order.DESC 반환
            //pathBuilder.get(order.getProperty()) :  정렬할 필드 가져옴.
        }
        return query;
    }
}

