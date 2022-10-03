package jpashop.jpashop.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import jpashop.jpashop.domain.Order;
import jpashop.jpashop.domain.OrderItem;
import jpashop.jpashop.domain.OrderItemId;
import jpashop.jpashop.repository.ItemRepository;
import jpashop.jpashop.repository.OrderItemRepository;
import jpashop.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest(properties = "test")
public class ItemRepositoryTest {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderItemRepository orderItemRepository;

    @Test
    public void LAZY_INIT_QUER발생확인 () {
        //given
        List<OrderItem> all = orderItemRepository.findAll();
        for (OrderItem orderItem : all) {
            orderItem.getOrder().getId(); // select query가 나가지 않는다. proxy라도 id값은 가지고 있기 때문
            orderItem.getOrder().getOrderStatus(); // select query가 발생한다. proxy는 id밖에 없기 때문

        }
    }

    @Test
    public void LAZY_N1_확인 (){
        //given
        Order order = orderRepository.findById(30000L).get();
        List<OrderItem> orderItemList = order.getOrderItemList();

        for(int i=0; i<orderItemList.size(); i++){
            System.out.println(orderItemList.get(i).getCount()); // i == 0 일때 where orderItem.order_id = 30000 으로 전부다 가져옴
            System.out.println(orderItemList.get(i).getItem()); //조회할때마다 item 조회 쿼리가 나감
        }
        System.out.println("e");
    }

    @Test
    public void EAGER_N1확인 (){
        //given
        List<Order> orders = orderRepository.findAll(); //Order.orderItemList가 EAGER일 경우.
        //order 전체 조회후
        //각 Order별 하나씩 orderItem들을 조회한다.
//        Hibernate: select order0_.order_id as order_id1_9_, order0_.order_date as order_da2_9_, order0_.order_update_date as order_up3_9_, order0_.delivery_id as delivery5_9_, order0_.member_id as member_i6_9_, order0_.order_status as order_st4_9_ from orders order0_
//        Hibernate: select orderiteml0_.order_order_id as order_or3_8_0_, orderiteml0_.item_item_id as item_ite4_8_0_, orderiteml0_.item_item_id as item_ite4_8_1_, orderiteml0_.order_order_id as order_or3_8_1_, orderiteml0_.count as count1_8_1_, orderiteml0_.order_price as order_pr2_8_1_ from order_item orderiteml0_ where orderiteml0_.order_order_id=?
//        Hibernate: select orderiteml0_.order_order_id as order_or3_8_0_, orderiteml0_.item_item_id as item_ite4_8_0_, orderiteml0_.item_item_id as item_ite4_8_1_, orderiteml0_.order_order_id as order_or3_8_1_, orderiteml0_.count as count1_8_1_, orderiteml0_.order_price as order_pr2_8_1_ from order_item orderiteml0_ where orderiteml0_.order_order_id=?

        //when

    }

    @Test
    public void 패치전략확인(){
        orderRepository.findById(30000L);
        /**
         *     @ManyToOne(fetch = FetchType.EAGER, optional = true/false)
         *     @JoinColumn(name = "MEMBER_ID", foreignKey = @ForeignKey(name = "ORDER_MEMBER_FK"))
         *     private Member member;
        */


        /**
          *OrderItem까지 가져오는건 테스트상 아래코드를 적용시켰을 뿐이니 넘어가자
         *     @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
         *     private List<OrderItem> orderItemList = new ArrayList<>();
        */
        //ToOne
        // optional = false  : 내부조인
        ////select order0_.order_id as order_id1_9_0_, order0_.order_date as order_da2_9_0_, order0_.order_update_date as order_up3_9_0_, order0_.delivery_id as delivery5_9_0_, order0_.member_id as member_i6_9_0_, order0_.order_status as order_st4_9_0_, member1_.member_id as member_i1_6_1_, member1_.member_create_date as member_c2_6_1_, member1_.member_update_date as member_u3_6_1_, member1_.city as city4_6_1_, member1_.street as street5_6_1_, member1_.zip_code as zip_code6_6_1_, member1_.name as name7_6_1_, member1_.password as password8_6_1_, orderiteml2_.order_order_id as order_or3_8_2_, orderiteml2_.item_item_id as item_ite4_8_2_, orderiteml2_.item_item_id as item_ite4_8_3_, orderiteml2_.order_order_id as order_or3_8_3_, orderiteml2_.count as count1_8_3_, orderiteml2_.order_price as order_pr2_8_3_ from orders order0_ inner join member member1_ on order0_.member_id=member1_.member_id left outer join order_item orderiteml2_ on order0_.order_id=orderiteml2_.order_order_id where order0_.order_id=?
        //optional = true : 외부조인
        ////select order0_.order_id as order_id1_9_0_, order0_.order_date as order_da2_9_0_, order0_.order_update_date as order_up3_9_0_, order0_.delivery_id as delivery5_9_0_, order0_.member_id as member_i6_9_0_, order0_.order_status as order_st4_9_0_, member1_.member_id as member_i1_6_1_, member1_.member_create_date as member_c2_6_1_, member1_.member_update_date as member_u3_6_1_, member1_.city as city4_6_1_, member1_.street as street5_6_1_, member1_.zip_code as zip_code6_6_1_, member1_.name as name7_6_1_, member1_.password as password8_6_1_, orderiteml2_.order_order_id as order_or3_8_2_, orderiteml2_.item_item_id as item_ite4_8_2_, orderiteml2_.item_item_id as item_ite4_8_3_, orderiteml2_.order_order_id as order_or3_8_3_, orderiteml2_.count as count1_8_3_, orderiteml2_.order_price as order_pr2_8_3_ from orders order0_ left outer join member member1_ on order0_.member_id=member1_.member_id left outer join order_item orderiteml2_ on order0_.order_id=orderiteml2_.order_order_id where order0_.order_id=?

        //toMany
        // 외부조인
    }

    @Test
    public void paging (){
        //given

        //when

        //then
    }
}
