package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor // 면 final이 붙은 필드를 모아서 생성자를 자동으로 만들어준다.
public class OrderServiceImpl implements OrderService{

    /* 아래 두 코드는 DIP, OCP 위반 중
    DIP : 추상(인터페이스) 뿐만 아니라 구체(구현) 클래스에도 의존하고 있다
    OCP : 기능을 확장해서 변경하면, 클라이언트 코드에 영향을 준다!
    //private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    //private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
    */

    // 생성자 주입을 사용함으로써 final 키워드로 생성자에서 값이 설정되지 않는 오류를 막아줌.
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    // 생성자 주입은 스프링의 빈 등록과 함께 일어남. 필수적으로 의존관계 주입할 때 사용.
    // setter 주입은 빈 등록 후 2번째로 일어남. 선택적으로 의존관계 주입할 때 사용.

/*  Lombok 사용으로 대체
    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }
*/

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
