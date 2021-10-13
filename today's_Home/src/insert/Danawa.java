package insert;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import data.NewDAO;
import data.NewVO;

public class Danawa {
	public static void main(String[] args) {
		Danawa m = new Danawa();
		m.getData();
	}

	public void getData() {
		NewDAO dao = new NewDAO();
		try {
			// 카테고리별~!
			String[] c1 = { "가구", "패브릭", "침구", "조명", "리모델링", "생활", "공구", "가전" };
			String[][] search = { 
					{ "서랍장", "소파", "침대프레임", "거실장", "행거", "탁자", "책상", "책장", "식탁", "선반", "신발장" },
					{ "커튼", "카페트" }, 
					{ "이불", "베게", "매트리스" }, 
					{ "전구", "조명" }, 
					{ "싱크대", "욕실리모델링", "샷시" },
					{ "수납상자", "홈데코" }, { "전동공구", "수공구" }, { "tv", "냉장고", "세탁기", "건조기" } };
			int total = 1;
			for (int i = 0; i < search.length; i++) {
				String cate1 = c1[i];
				for (int j = 0; j < search[i].length; j++) {
					String cate2 = search[i][j];
					Document doc = Jsoup
							.connect("http://search.danawa.com/dsearch.php?query=" + cate2 + "&tab=main&limit=120")
							.get();
					Elements items = doc.select(".product_list > li[id^=pr]");
					int no = 1 + ((i + 1) * 100 + j) * 100000; //(c1)(c2)(c2)(5자리는 item 번호)
					for (int k = 0; k < items.size(); k++) {
						try {
							Element item = items.get(k);
							NewVO vo = new NewVO();
							vo.setNo(no);
							vo.setCate1(cate1);
							vo.setCate2(cate2);
							String title = item.select(".prod_name").text();
							vo.setTitle(title);
							String cmt = item.select(".spec_list").text();
							vo.setCmt(cmt);
							String price = item.select(".price_sect strong").text();
							if (price.indexOf(' ') != -1)
								price = price.substring(0, price.indexOf(' '));
							vo.setPrice(price);
							String regdate = item.select(".mt_date > dd").text();
							vo.setRegdate(regdate);
							String score = item.select(".point_num strong").text();
							int dotIdx=score.indexOf('.');
							if(dotIdx!=-1) {
								score=score.substring(dotIdx-1,dotIdx+2);
								vo.setScore(Double.parseDouble(score));
							}
							String reviewCntStr = item.select(".cnt_opinion strong").text();
							String reviewCnt = "";
							for (int idx = 0; idx < reviewCntStr.length(); idx++) {
								if (reviewCntStr.charAt(idx) > '9' || reviewCntStr.charAt(idx) < '0')
									continue;
								reviewCnt += reviewCntStr.charAt(idx);
							}
							if (reviewCnt != "")
								vo.setReviewCnt(Integer.parseInt(reviewCnt));
							String img = item.select("img").attr("data-original");
							if (img == "")
								img = item.select("img").attr("src");
							vo.setImg(img);

							System.out.println("=====================================");
							System.out.println("no : " + vo.getNo());
							System.out.println("cate1 : " + vo.getCate1());
							System.out.println("cate1 : " + vo.getCate2());
							System.out.println("title : " + vo.getTitle());
							System.out.println("cmt : " + vo.getCmt());
							System.out.println("price : " + vo.getPrice());
							System.out.println("regdate : " + vo.getRegdate());
							System.out.println("score : " + vo.getScore());
							System.out.println("reviewCnt : " + vo.getReviewCnt());
							System.out.println("img : " + vo.getImg());
							no++;
							total++;
							// dao.dataInsert(vo);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
			System.out.println(total);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
