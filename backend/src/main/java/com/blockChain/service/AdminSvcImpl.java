package com.blockChain.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blockChain.domain.Auction;
import com.blockChain.domain.Bid;
import com.blockChain.domain.Celeb;
import com.blockChain.domain.Celeb_Group;
import com.blockChain.domain.Celeb_Like;
import com.blockChain.domain.GalleryArticle;
import com.blockChain.domain.Member;
import com.blockChain.domain.Member_Grade;
import com.blockChain.domain.Product;
import com.blockChain.domain.Product_Grade;
import com.blockChain.domain.Product_Grade_Percent;
import com.blockChain.domain.Product_Media;
import com.blockChain.domain.Product_Token;
import com.blockChain.domain.Reply;
import com.blockChain.domain.Sales;
import com.blockChain.domain.Sales_Like;
import com.blockChain.domain.Sales_Order;
import com.blockChain.domain.Sales_Product;
import com.blockChain.domain.Sold_Bundle_Inside;
import com.blockChain.domain.Token;
import com.blockChain.domain.Token_Owner;
import com.blockChain.dto.CardGenerateDTO;
import com.blockChain.repository.AuctionRepo;
import com.blockChain.repository.BidRepo;
import com.blockChain.repository.CelebRepo;
import com.blockChain.repository.Celeb_GroupRepo;
import com.blockChain.repository.Celeb_LikeRepo;
import com.blockChain.repository.GalleryArticleRepo;
import com.blockChain.repository.MemberRepo;
import com.blockChain.repository.Member_GradeRepo;
import com.blockChain.repository.ProductRepo;
import com.blockChain.repository.Product_GradeRepo;
import com.blockChain.repository.Product_Grade_PercentRepo;
import com.blockChain.repository.Product_MediaRepo;
import com.blockChain.repository.Product_TokenRepo;
import com.blockChain.repository.ReplyRepo;
import com.blockChain.repository.SalesRepo;
import com.blockChain.repository.Sales_LikeRepo;
import com.blockChain.repository.Sales_OrderRepo;
import com.blockChain.repository.Sales_ProductRepo;
import com.blockChain.repository.Sold_Bundle_InsideRepo;
import com.blockChain.repository.TokenRepo;
import com.blockChain.repository.Token_OwnerRepo;

@Service
@Transactional
public class AdminSvcImpl implements AdminSvcInter{
//	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
	final DateTimeFormatter formatter = new DateTimeFormatterBuilder()
		    .appendPattern("yyyyMMdd HH:mm:ss")
		    .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
		    .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
		    .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
		    .parseDefaulting(ChronoField.MILLI_OF_SECOND, 0)
		    .toFormatter();
	//????????????
    private final String SOLO = "??????";
    private final String GROUP1 = "?????????";
    private final String NONE = "?????????";
    
    //??????
    final int CELEBPEOPLES = 7;
//    final String[] CELEBS = {"?????????","?????????","?????????","?????????","?????????","??????","?????????"};
    final List<String> CELEBS = new ArrayList<String>(Arrays.asList("??????","?????????","??????","??????","GD","?????????","?????????"));//TODO
//    final List<String> CELEBS = new ArrayList<String>(Arrays.asList("?????????","?????????","?????????","?????????","?????????","??????","?????????"));
    final String[] MWs = {"W","W","W","W","M","W","M"};
    final String[] GROUP = {GROUP1,GROUP1,GROUP1,"??????","GD","?????????",NONE};
    final String[] DEBUT = {"20170201","20170201","20180201","20160201","20170201","20200201","19900201"};
    final String[] RETIRE = {"20190201","20200201",null,null,null,null,"20100201"};
    final String time = "09:00:00";
    //??????
    private final int MEMBER_NUMBER = 20;
    private final String[] MEMBER_GRADE= {"???","?????????","??????","?????????"};
    private final String MEMBER_ID_SAMPLE = "test";
    private final String MEMBER_NICK_SAMPLE = "?????????";
    private final String MEMBER_PW = "1234";
    
    private final String MEMBER_EMAIL = "test@test.com";
    private final String MEMBER_PHONE = "01011112222";
    
    //??????
    private final int TOKEN_NUM = 1200;
    private final int MAX_OWN = 10; //?????????????????? ???????????? ?????? ??? ?????? ?????? ?????? ???
    //??????????????????
    private final String[] PRODUCT_GRADE = {"SS","S","A","B","C"};
    private final Long[] PRODUCT_GRADE_PERCENT = {3L,7L,25L,30L,35L};
    private final String[] SPECIAL_CELEB = {"?????????","?????????","??????"};
//    private final String[] SPECIAL_CELEB = {"?????????","??????","?????????"};
    private final String[] SPECIAL_PRODUCT_GRADE = {"ONLY","ROYAL-A","ROYAL-B","ROYAL-C"};
    private final Long[] SPECIAL_PRODUCT_SHEETS = {1L,3L,7L,5L};
    private final int COMMON_CARD_NUM = 1000;//????????? ??? ???????????? ??????
    //??????(??????); ?????? ?????????
    private final int COMMON_CELEBS= 5;
    private final int SPECIAL_CELEBS=3;
    private final String[] JO = {"2018","2018","2017","2019","2019"};
    private final String[] NAM = {"2017","2018","2019","2020","2021"};
    private final String[] HA = {"2021","2020","2019","2018","2021"};
    private final String[] KIM = {"2019","2021","2020","2017","2018"};
    private final String[] SHIN = {"2018","2017","2019","2021","2020"};
    private final String[] LEE = {"JUST"};
    private final String[] NABI = {"2021","2020"};
    private final String[] SPKIM = {"2016"};
    private final String[][] COMMON_CARDLIST = {JO,NAM,HA,KIM,SHIN};
    private final String[][] SPECIAL_CARDLIST = {LEE,NABI,SPKIM};
    //?????? ????????? ??????
    private final String PRODUCT_MEDIA_ADRES = "/image/";
    //??????
    private final String SALES1 = "????????????";
    private final String SALES2 = "????????????";
    private final String SALES3 = "?????????";
    private final String SALES4 = "?????? ??????";
    private final String SALES5 = "2016~2018";
    private final String SALES6 = "2019~2021";
    private final List<String> SALES_LIST = new ArrayList<String>(Arrays.asList(SALES1,SALES2,SALES3,SALES4,SALES5,SALES6));
	private final String SALES_SUBFIX = "?????????";
	private final String MW = "MW";
	private final String group = "GR";
	private final String year = "YR";
	private final String celeb = "CL";
	private final List<String> SALES_DIV = new ArrayList<String>(Arrays.asList(MW,MW,group,group,year,year));
	//?????? ?????????
	private final int SL_COUNT = 15;
	//?????? ?????? ??????
	private final int SC_COUNT = 100;
	private final String[] REPLY_CON = {
			"????????? ?????? ???????????? ???\r\n" + 
			"\r\n" + 
			"????????? ?????? ???????????? ???",
			"?????? ?????? ?????? ?????? ???????????? ????????????\r\n" + 
			"????????? ????????? ????????? ?????? ??????\r\n" + 
			"?????? ?????? ?????? ?????? ???????????? ????????????\r\n" + 
			"????????? ????????? ??????????????? ?????? ???",
			"????????? ???????????? ?????? ??????\r\n" + 
			"????????? ????????? ??? ?????? ??????\r\n" + 
			"?????? ??? ????????????? ?????? ?????? ??????????\r\n" + 
			"?????? ?????? ?????? ??? ??? ?????? ??????\r\n" + 
			"????????? ????????? ???\r\n" + 
			"????????? ??? ?????? ??? ????????? ???\r\n" + 
			"????????? ????????? ????????? (???)\r\n" + 
			"??????????\r\n" + 
			"?????? ?????? ?????? ?????? ?????? ?????? ?????? ????????? (?????? ?????????)\r\n" + 
			"????????? ??????, ?????? ?????? ??????, ?????? ?????? ????????????\r\n" + 
			"????????? ???????????? ?????? ??????\r\n" + 
			"??? ?????? ????????? ??? ?????? ??????\r\n" + 
			"?????? ??? ????????????? ???????????? ??????????\r\n" + 
			"???????????? ?????? ?????? ????????????\r\n" + 
			"??? ?????? ????????? ???\r\n" + 
			"????????? ?????? ?????? ?????? ??? ???\r\n" + 
			"????????? ????????? ????????? (?????????, ??????, ???)"};
	//??????
	private final String AUCTION_NM = "?????? ????????????";
	private final String AUCTION_CON = "??? ?????? ??? ?????????\r\n" + 
			"??? ???????????? ?????? ?????????\r\n" + 
			"???????????? ???????????? ?????????\r\n" + 
			"????????? ????????? ?????????\r\n" + 
			"??? ????????? ?????????\r\n" + 
			"Mind control\r\n" + 
			"?????? ????????? ?????????\r\n" + 
			"??? ?????? ????????? ??????\r\n" + 
			"????????? ????????? ?????? ??? ????????????\r\n" + 
			"??? ?????? ??? ?????? ??????\r\n" + 
			"???????????? ?????? ?????????\r\n" + 
			"??? ?????? ???????????? ??? ?????????\r\n" + 
			"??? ?????? ??? ?????????\r\n" + 
			"??? ???????????? ?????? ?????????\r\n" + 
			"?????? ?????? ?????? ????????? ?????????\r\n" + 
			"?????? ?????? ?????? ????????? ?????????\r\n" + 
			"???????????? ???????????? ?????????\r\n" + 
			"????????? ????????? ?????????\r\n" + 
			"??? ????????? ?????????";
	@Autowired
	private PasswordEncoder passwordEncoder;
    @Autowired
	private CelebRepo celebRepo;
	@Autowired
	private Member_GradeRepo memberGradeRepo;
	@Autowired
	private MemberRepo memberRepo;
	@Autowired
	private Celeb_LikeRepo celebLikeRepo;
	@Autowired
	private TokenRepo tokenRepo;
	@Autowired
	private Token_OwnerRepo tokenOwnerRepo;
	@Autowired
	private Product_GradeRepo pgRepo;
	@Autowired
	private Product_Grade_PercentRepo pgpRepo;
	@Autowired
	private Celeb_GroupRepo celebGroupRepo;
	@Autowired
	private ProductRepo productRepo;
	@Autowired
	private Product_MediaRepo pmRepo;
	@Autowired
	private Product_TokenRepo ptRepo;
	@Autowired
	private SalesRepo salesRepo;
	@Autowired
	private Sales_ProductRepo spRepo;
	@Autowired
	private Sales_LikeRepo slRepo;
	@Autowired
	private ReplyRepo replyRepo;
	@Autowired
	private AuctionRepo auctionRepo;
	@Autowired
	private BidRepo bidRepo;
	@Autowired
	private Sales_OrderRepo soRepo;
	@Autowired
	private GalleryArticleRepo gaRepo;
	@Autowired
	private Sold_Bundle_InsideRepo sbiRepo;
	@Autowired
	private Token_OwnerRepo toRepo;
	
	@Autowired
	SalesSvcInter salesSvc;
	@Override
	public Map<String,Object> totalData(){
		Map<String, Object> res = new HashMap<String,Object>();
		res.put("insertCelebGroup",insertCelebGroup());
		res.put("insertCeleb", insertCeleb());
		res.put("insertMemberGrade", insertMemberGrade());
		res.put("insertMember", insertMember());
		res.put("insertCelebLike", insertCelebLike());
		res.put("insertToken", insertToken());
		res.put("insertProductGrade", insertProductGrade());
		res.put("insertProduct", insertProduct());
		res.put("insertProductMedia", insertProductMedia());
		res.put("insertProductToken", insertProductToken());
		res.put("insertSales", insertSales());
		res.put("insertSalesProduct",insertSalesProduct());
//		res.put("insertTokenOwner", insertTokenOwner());
//		res.put("buyCardPack", buyCardPack());
		res.put("insertSL", insertSL());
		res.put("reply", reply());
//		res.put("insertAuction",insertAuction());
//		res.put("insertBid", insertBid());
		res.put("insertGalleryArticle", insertGalleryArticle());
		return res;
	}
	//?????? ??????????????? ??????
	@Override
	public Map<String,Object> insertCeleb(){
	    Map<String, Object> res = new HashMap<String,Object>();
	    ArrayList<String> msg = new ArrayList<>();

	    for (int i = 0 ; i < CELEBPEOPLES;i++) {
	    	String tempGroup = GROUP[i];
	    	Optional<Celeb_Group> existOrNot = celebGroupRepo.sltByCelebGroupNM(tempGroup);
	    	System.out.println(tempGroup);
	    	System.out.println(existOrNot.isPresent());
	    	if(existOrNot.isPresent()) {
		    	Celeb celeb = new Celeb();
		    	celeb.setCelebNm(CELEBS.get(i));
		    	celeb.setCelebMw(MWs[i]);
		    	celeb.setGroup(existOrNot.get());
		    	LocalDateTime s = LocalDateTime.parse(DEBUT[i]+" "+time,formatter);
		    	LocalDateTime e = null;
		    	if (RETIRE[i] != null) {
		    		e = LocalDateTime.parse(RETIRE[i]+" "+time, formatter);
		    	}
		    	celeb.setCelebDebut(s);
		    	celeb.setCelebRetire(e);
		    	Integer temp= i;
 		    	Long pk = temp.longValue();
		    	celeb.setCelebNo(pk);
		    	celebRepo.save(celeb);
		    	msg.add(celeb.toString());
	    	}else {
	    		msg.add(tempGroup + "???????????? ??????");
	    	}
	    }
	    res.put("?????? ????????????", msg);
		return res;
		
	}
	//???????????? ??????????????? ??????
	@Override
	public Map<String,Object> insertCelebGroup(){
	    Map<String, Object> res = new HashMap<String,Object>();
//	    String[] celebGroup = {"?????????","?????????","??????", GROUP1, NONE};
//	    String[] celebGroup = {"??????","GD","?????????", GROUP1, NONE};
	    String[] celebGroup = {"?????????","GD","??????", GROUP1, NONE};
	    ArrayList<String> msg = new ArrayList<>();
	    Long[] peopleNum = { 1L,1L,1L,3L,0L};
	    for (int i = 0; i < celebGroup.length;i++) {
	    	Optional<Celeb_Group> existOrNot = celebGroupRepo.sltByCelebGroupNM(celebGroup[i]);
	    	System.out.println(existOrNot);
	    	if (existOrNot.isEmpty()){
		    	Celeb_Group celebGroupOne = new Celeb_Group();
		    	celebGroupOne.setGroupNo(Long.valueOf(i+1));
		    	celebGroupOne.setGroupNm(celebGroup[i]);
		    	celebGroupOne.setGroupNofp(peopleNum[i]);
		    	celebGroupRepo.save(celebGroupOne);
		    	msg.add(celebGroup[i] + peopleNum[i] + " ?????? ??????");
	    	}else {
	    		msg.add(celebGroup[i] + peopleNum[i] + " ?????? ??????");
	    	}
	    }
	    res.put("???????????? ????????????", msg);
	    return res;
	}
	//?????????????????? ???????????????
	@Override
	public Map<String,Object>insertMemberGrade(){
		Map<String, Object> res = new HashMap<String,Object>();
		String name = new Object() {}.getClass().getEnclosingMethod().getName();
		ArrayList<String> msg = new ArrayList<>();
		for(int i = 0; i < MEMBER_GRADE.length;i++){
			Member_Grade MG = new Member_Grade();
			MG.setMemberGradeNm(MEMBER_GRADE[i]);
			Member_Grade saved = memberGradeRepo.save(MG);
			msg.add(saved.toString());
		}
		res.put(name, msg);
		return res;
	}
	@Override
	public Map<String,Object>insertMember(){
		Map<String, Object> res = new HashMap<String,Object>();
		String name = new Object() {}.getClass().getEnclosingMethod().getName();
		ArrayList<String> msg = new ArrayList<>();
		List<Member_Grade> aa = memberGradeRepo.findAll();
		String pp = passwordEncoder.encode(MEMBER_PW);
		Random random = new Random();
		for (int i = 0; i < MEMBER_NUMBER;i++) {
			Member member = new Member();
			member.setMemberId(MEMBER_ID_SAMPLE + i);
			member.setMemberPw(pp);
			member.setMemberGrade(aa.get(random.nextInt(aa.size())));
			member.setMemberNick(MEMBER_NICK_SAMPLE + i);
			member.setMemberPhone(MEMBER_PHONE);
			member.setMemberEmail(MEMBER_EMAIL+i);
			Member saved = memberRepo.save(member);
			msg.add(saved.toString());
		}
		res.put(name, msg);
		return res;
	}
	
	
	@Override
	public Map<String,Object> insertCelebLike(){
		Map<String, Object> res = new HashMap<String,Object>();
		String name = new Object() {}.getClass().getEnclosingMethod().getName();
		ArrayList<String> msg = new ArrayList<>();
		List<Member> members = memberRepo.findAll();
		List<Celeb> celebs = celebRepo.findAll();
		int memberSize = members.size()-1;
		int celebSize = celebs.size();
		Random random = new Random();
		for(int i = 0; i < memberSize; i++){
			int check[] =new int[celebSize];
			Member tempMember = members.get(i);
			Celeb_Like celebLike = new Celeb_Like();
			int rand2 = random.nextInt(celebSize);
			celebLike.setCeleb(celebs.get(rand2));
			celebLike.setMember(tempMember);
			Celeb_Like saved = celebLikeRepo.save(celebLike);
			msg.add(saved.getMember().getMemberNo().toString()+" LIKE "+saved.getCeleb().getCelebNo().toString());
//			int rand = random.nextInt(celebSize);
//			for(int g = 0; g < rand; g++){
//				int rand2 = random.nextInt(celebSize);
//				if (check[rand2]==0){
//					check[rand2]=1;
//					Celeb_Like celebLike = new Celeb_Like();
//					celebLike.setCeleb(celebs.get(rand2));
//					celebLike.setMember(tempMember);
//					Celeb_Like saved = celebLikeRepo.save(celebLike);
//					msg.add(saved.getMember().getMemberNo().toString()+" LIKE "+saved.getCeleb().getCelebNo().toString());
//				}else {
//					g--;
//				}
//			}
		}
		res.put(name, msg);
		return res;
	}
	@Override
	public Map<String,Object> insertToken(){
		Map<String, Object> res = new HashMap<String,Object>();
		String name = new Object() {}.getClass().getEnclosingMethod().getName();
		ArrayList<String> msg = new ArrayList<>();
		for(int i = 0; i < TOKEN_NUM;i++) {
			Token token = new Token();
			token.setTokenSeriarlizeNo(Integer.toString(i));
			Token saved = tokenRepo.save(token);
			msg.add(saved.toString());
		}
		res.put(name, msg);
		return res;
	}
	@Override
	public Map<String,Object> buyCardPack(){
		Map<String, Object> res = new HashMap<String,Object>();
		String name = new Object() {}.getClass().getEnclosingMethod().getName();
		ArrayList<String> msg = new ArrayList<>();
		Random random = new Random();
		//????????? ?????????
		List<Sales> aa = salesRepo.findAll();
		List<Member> members = memberRepo.findAll();
		for(int g = 0 ; g < members.size();g++) {
			int randSale = random.nextInt(aa.size());
			int randBuy = random.nextInt(5)+10;
			Sales sales = aa.get(randSale); //????????? ?????????
			Member member = members.get(g); // ????????? ??????
			salesSvc.buyCardPack(aa.get(randSale).getSalesNo());
//			List<Token>tokens = tokenRepo.sltMultiBySales(sales);
			//????????? ?????? ?????? ??????
			for (int gg = 0; gg<randBuy; gg++) {
				List<Token>tokens = tokenRepo.sltMultiBySales(sales).orElseThrow(() -> new IllegalStateException("?????? ????????? ?????????????????????."));
				
				int sizes = tokens.size();
				Collections.shuffle(tokens);
				int CardpackSize = 5;
				
				if (sizes < CardpackSize) {
					CardpackSize= sizes;
				}
				List<Token> chooseTokens = tokens.subList(0, CardpackSize); //?????? 5??? ?????????
				Sales_Order salesOrder = new Sales_Order();
				salesOrder.setMember(member);
				salesOrder.setSales(sales);
				
				List<CardGenerateDTO> resCardList = new ArrayList<CardGenerateDTO>();
				Sales_Order savedOrder = soRepo.save(salesOrder); // ???????????? ??????
				for (int i =0; i<chooseTokens.size();i++) {
					Token tempToken = chooseTokens.get(i);
					Sold_Bundle_Inside sbi = new Sold_Bundle_Inside();
//					sbi.setSales(savedOrder.get);
					sbi.setToken(tempToken);
					sbiRepo.save(sbi); //?????? ???????????? ??????
					Token_Owner to = new Token_Owner();
					to.setMember(member);
					to.setToken(tempToken);
					to.setOwnDate(LocalDateTime.now());
					Token_Owner savedTo = toRepo.save(to); // ?????? ????????? ??????
					//????????? ?????? ????????????
					resCardList.add(productRepo.sltByTokenNo(savedTo.getToken()));
//				resTokens.add(savedTo.getToken());
				}
			}
		}
		return res;
	}
	@Override
	public Map<String,Object> insertTokenOwner(){//TODO
		Map<String, Object> res = new HashMap<String,Object>();
		String name = new Object() {}.getClass().getEnclosingMethod().getName();
		ArrayList<String> msg = new ArrayList<>();
		Random random = new Random();
		List<Member> members = memberRepo.findAll();
		List<Product_Token> tokens = ptRepo.findAll();
		int cnt = 0;
		for(int i = 0 ; i < members.size();i++) {
			int rand = random.nextInt(MAX_OWN)+5;
			int check[] =new int[tokens.size()];
			System.out.println(tokens.size());
			for (int g=0 ; g<rand; g++){
				int tokenRand = random.nextInt(tokens.size());
				if(check[tokenRand]==0) {
					cnt ++;
					check[tokenRand] = 1;
					Token_Owner tokenOwner = new Token_Owner();
					tokenOwner.setMember(members.get(i));
					tokenOwner.setToken(tokens.get(tokenRand).getToken());
					tokenOwner.setOwnDate(LocalDateTime.now().minusDays(cnt%10).minusMinutes(3*cnt));
					Token_Owner saved = tokenOwnerRepo.save(tokenOwner);
					
					msg.add(saved.getMember().getMemberNo() + " Own Token"+ saved.getToken().getTokenNo());
				}else {
					g--;
				}
			}
		}
		res.put(name, msg);
		return res;
	}
	@Override
	public Map<String, Object> insertProductGrade() {
		Map<String, Object> res = new HashMap<String,Object>();
		String name = new Object() {}.getClass().getEnclosingMethod().getName();
		ArrayList<String> msg = new ArrayList<>();
		//????????????
		for (int i = 0; i < SPECIAL_PRODUCT_GRADE.length;i++){
			Product_Grade pg =new Product_Grade();
			pg.setProductSheets(SPECIAL_PRODUCT_SHEETS[i]);
			pg.setProductGrade(SPECIAL_PRODUCT_GRADE[i]);
			pg.setProductPercent(null);
			Product_Grade saved = pgRepo.save(pg);
			msg.add(saved.toString());
		}
		//????????????
		for (int i = 0; i < PRODUCT_GRADE.length; i++) {
			Product_Grade pg =new Product_Grade();
			pg.setProductSheets(0L);
			pg.setProductGrade(PRODUCT_GRADE[i]);
			pg.setProductPercent(PRODUCT_GRADE_PERCENT[i]);
			pg.setProductSheets((COMMON_CARD_NUM*PRODUCT_GRADE_PERCENT[i])/100);
			Product_Grade saved = pgRepo.save(pg);
			msg.add(saved.toString());
		}
		res.put(name, msg);
		return res;
	}
	
	
	@Override
	public Map<String, Object> insertProduct() {
		Map<String, Object> res = new HashMap<String,Object>();
		String name = new Object() {}.getClass().getEnclosingMethod().getName();
		ArrayList<String> msg = new ArrayList<>();
		
		List<Product_Grade> pg= pgRepo.findAll(); 
		for(int c = 0; c<COMMON_CELEBS;c++){
			String celeb = CELEBS.get(c);
			String[] grades = COMMON_CARDLIST[c];
			Optional<Celeb> sltCeleb = celebRepo.sltCeleb(celeb);
			System.out.println(celeb);
			System.out.println(sltCeleb.toString());
			for(int i = 0; i<grades.length;i++){
				Optional<Product_Grade> sltGrade = pgRepo.sltbyGrade(PRODUCT_GRADE[i]);
				Product tempProduct = new Product();
				tempProduct.setCeleb(sltCeleb.get());
				tempProduct.setProductDetail("");
				tempProduct.setProductGrade(sltGrade.get());
				tempProduct.setProductNm(grades[i]+celeb);
				int percentConverter = PRODUCT_GRADE_PERCENT[i].intValue();
				tempProduct.setProductQuanty(COMMON_CARD_NUM/COMMON_CELEBS*percentConverter/100L);
				Product saved = productRepo.save(tempProduct);
				msg.add(saved.toString());
			}
		}
		int tempidx = 0;
		for (int c=0; c< SPECIAL_CELEBS; c++) {
			String celeb = SPECIAL_CELEB[c];
			String[] grades = SPECIAL_CARDLIST[c];
			Optional<Celeb> sltCeleb = celebRepo.sltCeleb(celeb);
			for(int i = 0; i<grades.length;i++){
				Optional<Product_Grade> sltGrade = pgRepo.sltbyGrade(PRODUCT_GRADE[i]);
				Product tempProduct = new Product();
				tempProduct.setCeleb(sltCeleb.get());
				tempProduct.setProductDetail("");
				tempProduct.setProductGrade(sltGrade.get());
				tempProduct.setProductNm(grades[i]+celeb);
				tempProduct.setProductQuanty(SPECIAL_PRODUCT_SHEETS[tempidx]);
				tempidx++;
				Product saved = productRepo.save(tempProduct);
				msg.add(saved.toString());
				System.out.println(saved.toString());
			}
		}
		res.put(name, msg);
		return res;
	}
	
	@Override
	public Map<String,Object> insertProductMedia(){
		Map<String, Object> res = new HashMap<String,Object>();
		String name = new Object() {}.getClass().getEnclosingMethod().getName();
		ArrayList<String> msg = new ArrayList<>();
		int cnt = 28;
		List<Product> product = productRepo.findAll();
		for(int i = 0; i <product.size();i++){
			Product_Media pm = new Product_Media();
			Product temp = product.get(i);
			pm.setProduct(temp);
			pm.setProductMediaAdres(PRODUCT_MEDIA_ADRES + temp.getProductNm()+cnt+".jpg");
			cnt--;
			Product_Media saved = pmRepo.save(pm);
			msg.add(saved.getProduct().getProductNo()+" image_where" + saved.getProductMediaAdres());
		}
		res.put(name, msg);
		return res;
	}
	@Override
	public Map<String,Object> insertProductToken(){
		Map<String, Object> res = new HashMap<String,Object>();
		String name = new Object() {}.getClass().getEnclosingMethod().getName();
		ArrayList<String> msg = new ArrayList<>();
		List<Token> tokens = tokenRepo.findAll();
		List<Product>products = productRepo.findAll();
		int[] viToken = new int[tokens.size()];
		int tokenIdx = 0;
		for (int i = 0; i <products.size();i++) {//????????? ????????????
			Product tempPro = products.get(i);
			Long quanty = tempPro.getProductQuanty();
			Long tempQuanty = 0L;
			for (Long g = 0L; g<quanty; g++) {
				Token token = tokens.get(tokenIdx);
				Product_Token pt= new Product_Token();
				pt.setProduct(tempPro);
				pt.setToken(token);
				tokenIdx++;
				tempQuanty++;
				Product_Token saved = ptRepo.save(pt);
				msg.add(saved.getToken().getTokenNo()+" IS "+saved.getProduct().getProductNo()+" "+tempQuanty+" / "+quanty);
				
			}
		}
		res.put(name, msg);
		return res;
	}
	
	@Override
	public Map<String,Object> insertSales(){
		//TODO
		Map<String, Object> res = new HashMap<String,Object>();
		String name = new Object() {}.getClass().getEnclosingMethod().getName();
		ArrayList<String> msg = new ArrayList<>();
		List<String>products = new ArrayList<>();
		products.addAll(SALES_LIST);
		for (int i = 0; i<products.size();i++) {
			Sales sales = new Sales();
			sales.setSalesDetail("???????????? ??????");
			sales.setSalesNm(products.get(i) +" "+ SALES_SUBFIX);
			sales.setSalesPrice(3L);
			sales.setSalesDiv(SALES_DIV.get(i));
			sales.setImgUrl(PRODUCT_MEDIA_ADRES+products.get(i));
			Sales saved = salesRepo.save(sales);
			msg.add(saved.toString());
		}
		products = new ArrayList<>();
		products.addAll(CELEBS);
		for (int i = 0; i<products.size();i++) {
			Sales sales = new Sales();
			sales.setSalesDetail("???????????? ??????");
			sales.setSalesNm(products.get(i) +" "+ SALES_SUBFIX);
			sales.setSalesPrice(3L);
			sales.setSalesDiv(celeb);
			sales.setImgUrl(PRODUCT_MEDIA_ADRES+products.get(i)+".jpg");
			Sales saved = salesRepo.save(sales);
			msg.add(saved.toString());
		}
		res.put(name, msg);
		return res;
	}
	@Override
	public Map<String,Object> insertSalesProduct(){
		//TODO
		Map<String, Object> res = new HashMap<String,Object>();
		String name = new Object() {}.getClass().getEnclosingMethod().getName();
		ArrayList<String> msg = new ArrayList<>();
//		Sales sales=new Sales();
		//??????
		String [] temp1 = {"??????","?????????","??????","??????","?????????"};
		msg.addAll(insertSPfactory(SALES1+" "+SALES_SUBFIX,temp1));
		//??????
		String [] temp2 = {"GD","?????????"};
		msg.addAll(insertSPfactory(SALES2+" "+SALES_SUBFIX,temp2));
		//?????????
		String [] temp3 = {"??????","?????????","??????"};
		msg.addAll(insertSPfactory(SALES3+" "+SALES_SUBFIX,temp3));
		//??????
		String[] temp4 = {"??????","GD","?????????"};
		msg.addAll(insertSPfactory(SALES4+" "+SALES_SUBFIX,temp4));
//		"??????","?????????","??????","??????","GD","?????????","?????????"
		String[] temp5 = {"??????","?????????","??????","??????","GD"};
//		String[] temp5 = {"?????????","?????????","?????????","?????????","?????????"};
		msg.addAll(insertSPfactory(SALES5+" "+SALES_SUBFIX,temp5));
		String[] temp6 = {"??????","?????????","??????","??????","GD","?????????"};
//		String[] temp6 = {"?????????","?????????","?????????","?????????","?????????","??????"};
		msg.addAll(insertSPfactory(SALES6+" "+SALES_SUBFIX,temp6));
		
		String[] celebs = {"??????","?????????","??????","??????","GD","?????????","?????????"};
		for (int i = 0; i< celebs.length; i++) {
			Celeb celeb = celebRepo.getById(Long.valueOf(i));
			Optional<List<Product>> products = productRepo.sltByCelebNo(celeb.getCelebNo());
			if (products.isPresent()) {
				List<Product> aa= products.get();
				Optional<Sales> sales = salesRepo.sltByContainSalesNM(celebs[i]);
				for(int g =0; g< aa.size();g++) {
					Sales_Product sp = new Sales_Product();
					sp.setProduct(aa.get(g));
					sp.setSales(sales.get());
					Sales_Product saved = spRepo.save(sp);
					msg.add(saved.getSales().getSalesNo()+" with " +saved.getProduct().getProductNo());
				}
				
			}
		}
		res.put(name, msg);
		return res;
	}
	public ArrayList<String> insertSPfactory(String div,String[] param){
		ArrayList<String> msg = new ArrayList<>();
		for(int i = 0; i< param.length;i++) {
			Optional<Sales> divs = salesRepo.sltBySalesNM(div);
			Sales_Product sp = new Sales_Product();
			Optional<List<Product>> products = productRepo.sltByCelebNM(param[i]);
			for (int g = 0; g< products.get().size(); g++) {
				sp.setSales(divs.get());
				sp.setProduct(products.get().get(g));
				Sales_Product saved = spRepo.save(sp);
				msg.add(saved.getSales().getSalesNo()+" with " +saved.getProduct().getProductNo());
			}
		}
		
		return msg;
	}
	//??????????????? ?????? ??????
	public Map<String,Object> insertSO(){
		Map<String, Object> res = new HashMap<String,Object>();
		String name = new Object() {}.getClass().getEnclosingMethod().getName();
		ArrayList<String> msg = new ArrayList<>();
		
		res.put(name, msg);
		return res;
	}
	@Override
	public Map<String,Object> insertSL(){
		Map<String, Object> res = new HashMap<String,Object>();
		String name = new Object() {}.getClass().getEnclosingMethod().getName();
		ArrayList<String> msg = new ArrayList<>();
		List<Member> member = memberRepo.findAll();
		List<Sales> sales = salesRepo.findAll();
		Random random = new Random();
		
		for (int i = 0;i<sales.size();i++) {
			Sales saleOne = sales.get(i);
			int randLike = random.nextInt(15);
			for (int g = 0; g< randLike; g++) {
				Sales_Like sl = new Sales_Like();
				int randMemeber = random.nextInt(member.size());
				sl.setMember(member.get(randMemeber));
				sl.setSales(saleOne);
				Sales_Like saved = slRepo.save(sl);
				msg.add(saved.getMember().getMemberNo()+" LIKE "+saved.getSales());
			}
		}
		
		res.put(name, msg);
		return res;
	}
	@Override
	public Map<String,Object> reply(){//TODO
		Map<String, Object> res = new HashMap<String,Object>();
		String name = new Object() {}.getClass().getEnclosingMethod().getName();
		ArrayList<String> msg = new ArrayList<>();
		
		List<Member> member = memberRepo.findAll();
		List<Sales> sales = salesRepo.findAll();
		Random random = new Random();
		int testReply = 0;
		for (int i = 0; i < sales.size();i++) {
			for (int g = 0; g< member.size(); g++) {
				testReply++;
				int testReplyContent = random.nextInt(REPLY_CON.length);
				Reply reply = new Reply();
				reply.setMember(member.get(g));
				reply.setReplyContent(testReply+ " " + REPLY_CON[testReplyContent]);
				reply.setSales(sales.get(i));
				//????????? ????????? ?????? ???????????? ????????? ?????? ??????
				reply.setReplyDate(LocalDateTime.now().minusDays(3).plusMinutes(testReply));
				Reply saved = replyRepo.save(reply);
				msg.add(saved.getMember().getMemberNo()+" reply at "+saved.getSales().getSalesNo()+ " "+ testReply);
			}
		}
		res.put(name, msg);
		return res;
	}
	@Override
	public Map<String,Object> insertAuction(){
		//????????? ????????? ?????? ????????? ????????? ????????? ???????????? ??????.
		Map<String, Object> res = new HashMap<String,Object>();
		String name = new Object() {}.getClass().getEnclosingMethod().getName();
		ArrayList<String> msg = new ArrayList<>();
		Optional<List<Member>> member = tokenOwnerRepo.sltMultiOwner();
		Random random = new Random();
		int cnt = 0;
		for (int i = 0; i< member.get().size();i++) {
			Long memberNo = member.get().get(i).getMemberNo();
			Optional<List<Token_Owner>> tokenOwner = tokenOwnerRepo.sltMultiTokenByMember(memberNo);
			
			int tokensQuanty = tokenOwner.get().size();
			int randTokens = random.nextInt(tokensQuanty);
			for (int g = 0 ; g< randTokens;g++) {
				int ranToken = random.nextInt(tokensQuanty);
				cnt++;
				LocalDateTime startline = LocalDateTime.now().minusDays(3).plusMinutes(cnt);
				LocalDateTime deadline = startline.plusWeeks(2);
				Auction auction = new Auction();
				Integer randPrice = 1 + random.nextInt(5);
				auction.setAuctionDetail(AUCTION_CON);
				auction.setAuctionImmeprice(randPrice.longValue());
				auction.setAuctionName(AUCTION_NM +" "+cnt);
				auction.setAuctionStart(startline);
				auction.setAuctionDeadline(deadline);
				auction.setMember(member.get().get(i));
				auction.setToken(tokenOwner.get().get(randTokens).getToken());
				Auction saved = auctionRepo.save(auction);
				msg.add(saved.getMember().getMemberNo()+" regist Auction : "+ saved.getToken().getTokenNo());
			}
		}
		
		
		res.put(name, msg);
		return res;
	}
	@Override
	public Map<String,Object> insertBid(){
		Map<String, Object> res = new HashMap<String,Object>();
		String name = new Object() {}.getClass().getEnclosingMethod().getName();
		ArrayList<String> msg = new ArrayList<>();
		List<Member> member = memberRepo.findAll();
		List<Auction>auction = auctionRepo.findAll();
		Random random = new Random();
		for (int g = 0; g<auction.size(); g++) {
			int randBidMember = random.nextInt(member.size());
			int randBidQuanty = 20+random.nextInt(4);
			LocalDateTime startTime = auction.get(g).getAuctionStart();
			Integer cnt = 0;
			for (int i = 0; i<randBidQuanty;i++){
				cnt++;
				Bid bid = new Bid();
				bid.setAuction(auction.get(g));
				bid.setBidDate(startTime.plusMinutes(cnt));
				bid.setBidPrice(cnt.longValue());
				bid.setMember(member.get(randBidMember));
				Bid saved = bidRepo.save(bid);
				msg.add(saved.getAuction().getAuctionNo()+" bid at "+saved.getAuction().getAuctionNo()+" pice: "+saved.getBidPrice());
			}
		}
		res.put(name, msg);
		return res;
	}
	public Map<String,Object> insertGalleryArticle(){
		Map<String, Object> res = new HashMap<String,Object>();
		String name = new Object() {}.getClass().getEnclosingMethod().getName();
		ArrayList<String> msg = new ArrayList<>();
		List<Member> member = memberRepo.findAll();
		Random random = new Random();
		int cnt = 0;
		for(int g = 0; g<member.size();g++){
			int ran = random.nextInt(4);
			for(int gg = 0; gg<ran; gg++) {
				cnt ++;
				GalleryArticle ga = new GalleryArticle();
				ga.setGalleryArticleContent("????????? ????????? ????????? "+cnt);
				ga.setMember(member.get(g));
				GalleryArticle saved = gaRepo.save(ga);
				msg.add(saved.getMember().getMemberNo()+ "insert" + saved.getGalleryArticleNo() +" "+ saved.getGalleryArticleContent()) ;
			}
		
		}
		res.put(name, msg);
		return res;
		}
	public Map<String,Object> sample(){
		Map<String, Object> res = new HashMap<String,Object>();
		String name = new Object() {}.getClass().getEnclosingMethod().getName();
		ArrayList<String> msg = new ArrayList<>();
		res.put(name, msg);
		return res;
	}
	}
