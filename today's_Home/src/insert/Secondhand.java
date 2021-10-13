package insert;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import data.SecondhandDAO;
import data.SecondhandVO;

public class Secondhand {
	public static void main(String[] args) {
		Secondhand m = new Secondhand();
		m.getData();
	}

	public void getData() {
		SecondhandDAO dao= new SecondhandDAO();
		try {
			String[] cate1s = {"", "사무", "OA", "냉난방", "가전", "가정" };
			Document doc = Jsoup.connect("http://www.remarkety.co.kr/shop").get();
			Elements e = doc.select(".category-ul a");
			String cate2="";
			int no=1;
			for (int i = 0; i < e.size(); i++) {
				String link = e.get(i).attr("href");
				//System.out.println(link);
				String c2 = e.get(i).text();
				//System.out.println(c2);
				String num = link.substring(link.lastIndexOf("=") + 1);
				//System.out.println(num);

				if (num.length() == 4) {
					cate2 = c2;
					//System.out.println(cate2);
					continue;
				}

				if (num.length() == 6) {
					int c3Num = Integer.parseInt(num);
					//System.out.println(c3Num);
					String cate3=e.get(i).text();
					//System.out.println(cate3);
					for (int j = 1;; j++) {
						Document doc2 = Jsoup.connect("http://www.remarkety.co.kr" + link + "&sort=&sortodr=&page=" + j)
								.get();
						Elements img = doc2.select(".sct_img img");
						if (img.size() == 0)	break;
						Elements title = doc2.select(".sct_txt");
						Elements comment = doc2.select(".sct_basic");
						Elements price = doc2.select(".sct_cost");
						for(int k=0;k<img.size();k++) {
							try {
								SecondhandVO vo=new SecondhandVO();
								int c1Num=c3Num/10000;			
								vo.setNo(no);
								vo.setC1Num(c1Num);
								vo.setC2Num(c3Num/100);
								vo.setC3Num(c3Num);
								vo.setCate1(cate1s[c1Num/10]);
								vo.setCate2(cate2);
								vo.setCate3(cate3);
								vo.setCmt(comment.get(k).text());
								vo.setImg(img.get(k).attr("src"));
								vo.setPrice(price.get(k).text());
								vo.setTitle(title.get(k).text());
								
								System.out.println(no);
								System.out.println(c1Num);
								System.out.println(c3Num/100);
								System.out.println(c3Num);
								System.out.println(cate1s[c1Num/10]);
								System.out.println(cate2);
								System.out.println(cate3);
								System.out.println(comment.get(k).text());
								System.out.println(img.get(k).attr("src"));
								System.out.println(price.get(k).text());
								System.out.println(title.get(k).text());
								System.out.println("==========================================");
								//dao.dataInsert(vo);
								//dao.imgInsert(img.get(k).attr("src"), no);
								no++;
							} catch (Exception e2) {}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
