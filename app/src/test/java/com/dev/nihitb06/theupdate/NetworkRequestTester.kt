package com.dev.nihitb06.theupdate

import com.dev.nihitb06.theupdate.data.database.ArticleEntity
import com.dev.nihitb06.theupdate.data.network.requests.ArticleResponseObject
import com.dev.nihitb06.theupdate.data.network.requests.ResponseParser
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.junit.Test

class NetworkRequestTester {

    private val json = "{\"status\":\"ok\",\"totalResults\":3217,\"articles\"" +
            ":[{\"source\":{" +
            "\"id\":null," +
            "\"name\":\"Tribunnews.com\"" +
            "},\"author\":\"Mohammad Rifan Aditya\"," +
            "\"title\":\"Harga dan Spesifikasi Honor V20 Terbaru Punya Kamera 48 MP, Saingan Berat dari Xiaomi - Tribun Style\"," +
            "\"description\":\"Honor secara resmi akan segera meluncurkan hape terbarunya dengan kamera 48 MP yaitu Honor V20 (View 20), berikut ini harga dan spesifikasinya.\"," +
            "\"url\":\"http://style.tribunnews.com/2018/12/28/harga-dan-spesifikasi-honor-v20-terbaru-punya-kamera-48-mp-saingan-berat-dari-xiaomi\"," +
            "\"urlToImage\":\"http://cdn2.tstatic.net/style/foto/bank/images/peluncuran-honor-v20-view-20.jpg\"," +
            "\"publishedAt\":\"2018-12-28T08:25:00Z\"," +
            "\"content\":\"null\"" +
            "}]}"
    private val category = "technology"

    private val jsonOne = "{\n" +
            "\n" +
            "    \"status\": \"ok\",\n" +
            "    \"totalResults\": 36,\n" +
            "    -\n" +
            "    \"articles\": [\n" +
            "        -\n" +
            "        {\n" +
            "            -\n" +
            "            \"source\": {\n" +
            "                \"id\": \"cnn\",\n" +
            "                \"name\": \"CNN\"\n" +
            "            },\n" +
            "            \"author\": \"Manveena Suri, CNN\",\n" +
            "            \"title\": \"At least 15 killed as Bangladesh election turns violent - CNN\",\n" +
            "            \"description\": \"At least 15 people were killed in election-related clashes in Bangladesh on Sunday, local police officials told CNN.\",\n" +
            "            \"url\": \"https://www.cnn.com/2018/12/30/asia/bangladesh-election-sheikh-hasina-intl/index.html\",\n" +
            "            \"urlToImage\": \"https://cdn.cnn.com/cnnnext/dam/assets/181230110225-bangadesh-election-super-tease.jpg\",\n" +
            "            \"publishedAt\": \"2018-12-30T11:26:00Z\",\n" +
            "            \"content\": null\n" +
            "        },\n" +
            "        -\n" +
            "        {\n" +
            "            -\n" +
            "            \"source\": {\n" +
            "                \"id\": null,\n" +
            "                \"name\": \"Youtube.com\"\n" +
            "            },\n" +
            "            \"author\": null,\n" +
            "            \"title\": \"Amanda Nunes speaks after historic victory over Cyborg | INTERVIEW | UFC 232 - UFC ON FOX\",\n" +
            "            \"description\": \"Nunes spoke to the UFC on FOX crew after her historic KO victory over Cris Cyborg on Saturday. #UFConFOX #UFC232 #AmandaNunes #CrisCyborg SUBSCRIBE for more ...\",\n" +
            "            \"url\": \"https://www.youtube.com/watch?v=1XfWsKaauHg\",\n" +
            "            \"urlToImage\": \"https://i.ytimg.com/vi/1XfWsKaauHg/maxresdefault.jpg\",\n" +
            "            \"publishedAt\": \"2018-12-30T08:23:18Z\",\n" +
            "            \"content\": \"Rating is available when the video has been rented. This feature is not available right now. Please try again later. Loading playlists...\"\n" +
            "        },\n" +
            "        -\n" +
            "        {\n" +
            "            -\n" +
            "            \"source\": {\n" +
            "                \"id\": \"nbc-news\",\n" +
            "                \"name\": \"NBC News\"\n" +
            "            },\n" +
            "            \"author\": \"Associated Press\",\n" +
            "            \"title\": \"Oregon hotel fires employees who asked black guest to leave - NBCNews.com\",\n" +
            "            \"description\": \"An Oregon hotel said it fired two of its employees for \\\"mistreatment\\\" of a black guest who was talking on his phone in the lobby when he was asked to leave a week ago.\",\n" +
            "            \"url\": \"https://www.nbcnews.com/news/nbcblk/oregon-hotel-fires-employees-who-asked-black-guest-leave-n953026\",\n" +
            "            \"urlToImage\": \"https://nodeassets.nbcnews.com/cdnassets/projects/socialshareimages-bento/og-nbcnews1200x630.png\",\n" +
            "            \"publishedAt\": \"2018-12-30T08:05:00Z\",\n" +
            "            \"content\": \"Dec. 30, 2018 / 8:05 AM GMT PORTLAND, Ore. An Oregon hotel said it fired two of its employees for \\\"mistreatment\\\" of a black guest who was talking on his phone in the lobby when he was asked to leave a week ago. DoubleTree by Hilton hotel in Portland tweeted S… [+1753 chars]\"\n" +
            "        },\n" +
            "        -\n" +
            "        {\n" +
            "            -\n" +
            "            \"source\": {\n" +
            "                \"id\": \"the-new-york-times\",\n" +
            "                \"name\": \"The New York Times\"\n" +
            "            },\n" +
            "            \"author\": null,\n" +
            "            \"title\": \"A Day, a Life: When a Medic Was Killed in Gaza, Was It an Accident? - The New York Times\",\n" +
            "            \"description\": \"When an Israeli soldier killed Rouzan al-Najjar, Palestinians called her an innocent martyr and Israel portrayed her as a threat. The truth is more complicated.\",\n" +
            "            \"url\": \"https://www.nytimes.com/2018/12/30/world/middleeast/gaza-medic-israel-shooting.html\",\n" +
            "            \"urlToImage\": \"https://static01.nyt.com/images/2018/12/24/world/30gaza-protest/merlin_138969837_a3981295-aeef-42ea-837a-678e2f40ef56-facebookJumbo.jpg\",\n" +
            "            \"publishedAt\": \"2018-12-30T07:59:27Z\",\n" +
            "            \"content\": \"KHUZAA, Gaza Strip A young medic in a head scarf runs into danger, her only protection a white lab coat. Through a haze of tear gas and black smoke, she tries to reach a man sprawled on the ground along the Gaza border. Israeli soldiers, their weapons leveled… [+2916 chars]\"\n" +
            "        },\n" +
            "        -\n" +
            "        {\n" +
            "            -\n" +
            "            \"source\": {\n" +
            "                \"id\": \"reuters\",\n" +
            "                \"name\": \"Reuters\"\n" +
            "            },\n" +
            "            \"author\": \"Hyonhee Shin\",\n" +
            "            \"title\": \"North Korea's Kim wants more summits with Moon next year: Blue House - Reuters\",\n" +
            "            \"description\": \"North Korean leader Kim Jong Un said he wants to hold more summits with South Korea's Moon Jae-in next year to achieve the goal of denuclearization of the Korean peninsula, Moon's office said on Sunday.\",\n" +
            "            \"url\": \"https://www.reuters.com/article/us-northkorea-southkorea/north-koreas-kim-wants-more-summits-with-moon-next-year-blue-house-idUSKCN1OT06I\",\n" +
            "            \"urlToImage\": \"https://s4.reutersmedia.net/resources/r/?m=02&d=20181230&t=2&i=1340354713&w=1200&r=LYNXNPEEBT059\",\n" +
            "            \"publishedAt\": \"2018-12-30T07:59:00Z\",\n" +
            "            \"content\": \"SEOUL (Reuters) - North Korean leader Kim Jong Un said he wants to hold more summits with South Korea’s Moon Jae-in next year to achieve the goal of denuclearization of the Korean peninsula, Moon’s office said on Sunday. Kim sent a letter to Moon on Sunday to… [+1187 chars]\"\n" +
            "        },\n" +
            "        -\n" +
            "        {\n" +
            "            -\n" +
            "            \"source\": {\n" +
            "                \"id\": null,\n" +
            "                \"name\": \"Nypost.com\"\n" +
            "            },\n" +
            "            \"author\": \"Zach Braziller\",\n" +
            "            \"title\": \"Brian Kelly: This brutal playoff loss feels different than 2012 - New York Post \",\n" +
            "            \"description\": \"ARLINGTON, Texas — Notre Dame coach Brian Kelly didn’t see any similarities. At least, he didn’t want to admit there were any. When asked if he would compare Saturday’s 30-3 defeat to Clemson to th…\",\n" +
            "            \"url\": \"https://nypost.com/2018/12/30/brian-kelly-this-brutal-playoff-loss-feels-different-than-2012/\",\n" +
            "            \"urlToImage\": \"https://thenypost.files.wordpress.com/2018/12/Brian-Kelly.jpg?quality=90&strip=all&w=1144\",\n" +
            "            \"publishedAt\": \"2018-12-30T07:33:00Z\",\n" +
            "            \"content\": \"ARLINGTON, Texas Notre Dame coach Brian Kelly didnt see any similarities. At least, he didnt want to admit there were any. When asked if he would compare Saturdays 30-3 defeat to Clemson to the 42-14 rout at the hands of Alabama in the 2012 BCS Championship g… [+1469 chars]\"\n" +
            "        },\n" +
            "        -\n" +
            "        {\n" +
            "            -\n" +
            "            \"source\": {\n" +
            "                \"id\": \"fox-news\",\n" +
            "                \"name\": \"Fox News\"\n" +
            "            },\n" +
            "            \"author\": \"Louis Casiano\",\n" +
            "            \"title\": \"Ocasio-Cortez slams McCaskill after departing Dem calls her a 'thing' and a 'shiny object' - Fox News\",\n" +
            "            \"description\": \"U.S. Rep. elect Alexandria Ocasio-Cortez responded to outgoing Sen. Claire McCaskill comments earlier in the week when she called the New York Democrat a “bright shiny new object.”\",\n" +
            "            \"url\": \"https://www.foxnews.com/politics/ocasio-cortez-responds-to-mccaskill-over-criticsm\",\n" +
            "            \"urlToImage\": \"https://static.foxnews.com/foxnews.com/content/uploads/2018/12/1d15d9f4-Capture.jpg\",\n" +
            "            \"publishedAt\": \"2018-12-30T07:27:50Z\",\n" +
            "            \"content\": \"U.S. Rep.-elect Alexandria Ocasio-Cortez responded to comments made by outgoing Sen. Claire McCaskill earlier this week in which she called the New York Democrat a “bright shiny new object.” \\\"Not sure why fmr Sen. McCaskill keeps going on TV to call me a 'thi… [+1717 chars]\"\n" +
            "        },\n" +
            "        -\n" +
            "        {\n" +
            "            -\n" +
            "            \"source\": {\n" +
            "                \"id\": \"abc-news\",\n" +
            "                \"name\": \"ABC News\"\n" +
            "            },\n" +
            "            \"author\": \"Mark Osborne\",\n" +
            "            \"title\": \"Cyberattack targets newspapers in US, prevents some from publishing - ABC News\",\n" +
            "            \"description\": \"The DHS confirmed it was investigating the incidents.\",\n" +
            "            \"url\": \"https://abcnews.go.com/US/cyberattack-targets-newspapers-us-prevents-publishing/story?id=60074602\",\n" +
            "            \"urlToImage\": \"https://s.abcnews.com/images/US/la-times-building-ap-mo-20181230_hpMain_16x9_992.jpg\",\n" +
            "            \"publishedAt\": \"2018-12-30T07:08:00Z\",\n" +
            "            \"content\": \"Several U.S. newspapers came under attack from apparent hackers on Saturday, preventing some from printing and distributing their daily editions. The Los Angeles Times, San Diego Union-Tribune, Chicago Tribune and Baltimore Sun were among the major newspapers… [+2367 chars]\"\n" +
            "        },\n" +
            "        -\n" +
            "        {\n" +
            "            -\n" +
            "            \"source\": {\n" +
            "                \"id\": \"the-huffington-post\",\n" +
            "                \"name\": \"The Huffington Post\"\n" +
            "            },\n" +
            "            \"author\": \"Carla Herreria\",\n" +
            "            \"title\": \"6 Times Trump 'Hit A New Low' In 2018 - HuffPost\",\n" +
            "            \"description\": \"A congressman said Trump hit “a new low” with his latest tweets — but this isn’t the first time the president has sunk to a “new low.”\",\n" +
            "            \"url\": \"https://www.huffingtonpost.com/entry/trump-new-lows-2018_us_5c2822b5e4b0407e90834350\",\n" +
            "            \"urlToImage\": \"https://img.huffingtonpost.com/asset/5c285a0f2200003708debd1a.jpeg?cache=ATU61azNpz&ops=1200_630\",\n" +
            "            \"publishedAt\": \"2018-12-30T06:39:00Z\",\n" +
            "            \"content\": \"Less than three days before the year’s end and President Donald Trump has managed to shock his critics with more insensitive rants. This time, it was about the immigrant children who recently died while in the custody of Customs and Border Patrol after surren… [+10173 chars]\"\n" +
            "        },\n" +
            "        -\n" +
            "        {\n" +
            "            -\n" +
            "            \"source\": {\n" +
            "                \"id\": \"al-jazeera-english\",\n" +
            "                \"name\": \"Al Jazeera English\"\n" +
            "            },\n" +
            "            \"author\": \"Hamza Mohamed\",\n" +
            "            \"title\": \"DRC election: Polls open in long-delayed vote - Aljazeera.com\",\n" +
            "            \"description\": \"More than 46 million Congolese have registered to elect the successor to President Joseph Kabila.\",\n" +
            "            \"url\": \"https://www.aljazeera.com/news/2018/12/drc-election-polls-open-long-delayed-vote-181230055430093.html\",\n" +
            "            \"urlToImage\": \"https://www.aljazeera.com/mritems/Images/2018/12/30/4579e6ca99c34aa6a39f9554abcdf360_18.jpg\",\n" +
            "            \"publishedAt\": \"2018-12-30T06:12:00Z\",\n" +
            "            \"content\": \"Kinshasa, DRC - Polling stations in the Democratic Republic of the Congo 's (DRC's) long-delayed election have opened, two years after they were first scheduled to be held. Voting stations opened at 5am (04:00 GMT) on Sunday and will close at 5pm (16:00 GMT).… [+3371 chars]\"\n" +
            "        },\n" +
            "        -\n" +
            "        {\n" +
            "            -\n" +
            "            \"source\": {\n" +
            "                \"id\": null,\n" +
            "                \"name\": \"Nj.com\"\n" +
            "            },\n" +
            "            \"author\": \"Michael Sol Warren | NJ Advance Media for NJ.com\",\n" +
            "            \"title\": \"Patient tests positive for hep C, but surgery center says 'anyone can feel safe about coming here' - NJ.com\",\n" +
            "            \"description\": \"HealthPlus Surgery Center, which possibly exposed thousands of patients to HIV, responded on Saturday to the release of a state report blasting the facility's past infection control practices.\",\n" +
            "            \"url\": \"https://www.nj.com/news/2018/12/patient-tests-positive-for-hep-c-but-surgery-center-says-anyone-can-feel-safe-about-coming-here.html\",\n" +
            "            \"urlToImage\": \"https://i.nj.com/resizer/n-tLKpaAgQernmiOC47G12ZSLp8=/620x0/arc-anglerfish-arc2-prod-advancelocal/public/OHRT2YSQK5D2BNBPWLLPX7W7XM.jpg\",\n" +
            "            \"publishedAt\": \"2018-12-30T06:02:00Z\",\n" +
            "            \"content\": \"The North Jersey surgery center that possibly exposed thousands of patients to HIV and hepatitis B and C responded on Saturday to the release of a state report that blasted the facilitys past infection control practices. Representatives of the HealthPlus Surg… [+5554 chars]\"\n" +
            "        },\n" +
            "        -\n" +
            "        {\n" +
            "            -\n" +
            "            \"source\": {\n" +
            "                \"id\": \"the-guardian-au\",\n" +
            "                \"name\": \"The Guardian (AU)\"\n" +
            "            },\n" +
            "            \"author\": \"David Taylor\",\n" +
            "            \"title\": \"How Nancy Pelosi signaled the end of Donald Trump's easy ride - The Guardian\",\n" +
            "            \"description\": \"In one deft performance the top Democrat in the House owned the president, having faced down Republicans scare tactics and attacks from her own side\",\n" +
            "            \"url\": \"https://www.theguardian.com/us-news/2018/dec/30/nancy-pelosi-trumps-easy-ride-ends-with-democrat-speaker-house\",\n" +
            "            \"urlToImage\": \"https://i.guim.co.uk/img/media/b0c81b898247e2c1b79fe33b09183bf3c4b2d8c4/0_139_5472_3283/master/5472.jpg?width=1200&height=630&quality=85&auto=format&fit=crop&overlay-align=bottom%2Cleft&overlay-width=100p&overlay-base64=L2ltZy9zdGF0aWMvb3ZlcmxheXMvdGctZGVmYXVsdC5wbmc&s=0aa419679fe5bb873b8c17885f29bdfa\",\n" +
            "            \"publishedAt\": \"2018-12-30T06:00:00Z\",\n" +
            "            \"content\": \"Nancy Pelosi was perched on the end of a sofa in the Oval Office when the balance of power in Donald Trumps Washington decisively shifted in her favour.. The event in early December began as a simple photo call with Trump the first attempt at bipartisan dialo… [+7102 chars]\"\n" +
            "        },\n" +
            "        -\n" +
            "        {\n" +
            "            -\n" +
            "            \"source\": {\n" +
            "                \"id\": null,\n" +
            "                \"name\": \"Sbnation.com\"\n" +
            "            },\n" +
            "            \"author\": \"Alex Kirshner\",\n" +
            "            \"title\": \"Playoff National Championship matchup set: Alabama vs. Clemson date, time, and more - SB Nation\",\n" +
            "            \"description\": \"College football’s biggest game has its matchup. It’s January 7 in Santa Clara.\",\n" +
            "            \"url\": \"https://www.sbnation.com/college-football/2018/12/29/18159237/national-championship-date-time-teams-2019\",\n" +
            "            \"urlToImage\": \"https://cdn.vox-cdn.com/thumbor/nrE_9aisod5FXOqWdPg7gW4-GDE=/0x159:1135x753/fit-in/1200x630/cdn.vox-cdn.com/uploads/chorus_asset/file/8612571/631364608.jpg\",\n" +
            "            \"publishedAt\": \"2018-12-30T04:47:57Z\",\n" +
            "            \"content\": \"The College Football Playoff National Championship is nearly upon us. The teams are exactly who weve come to expect to see in this game. Whos playing in the Playoff National Championship? Clemson (14-0, 8-0 in ACC) The Tigers, playing in the Playoff for the f… [+2846 chars]\"\n" +
            "        },\n" +
            "        -\n" +
            "        {\n" +
            "            -\n" +
            "            \"source\": {\n" +
            "                \"id\": \"cnn\",\n" +
            "                \"name\": \"CNN\"\n" +
            "            },\n" +
            "            \"author\": \"Leona Siaw  and Susannah Cullinane\",\n" +
            "            \"title\": \"'Absolutely Fabulous' actress Dame June Whitfield dies at 93 - CNN\",\n" +
            "            \"description\": \"British actress Dame June Whitfield --  perhaps best known for her role in the sitcom \\\"Absolutely Fabulous\\\" -- has died at age 93.\",\n" +
            "            \"url\": \"https://www.cnn.com/2018/12/29/entertainment/absolutely-fabulous-actress-dame-june-whitfield-dies/index.html\",\n" +
            "            \"urlToImage\": \"https://cdn.cnn.com/cnnnext/dam/assets/181230162154-june-whitfield-dame-super-tease.jpg\",\n" +
            "            \"publishedAt\": \"2018-12-30T04:44:00Z\",\n" +
            "            \"content\": null\n" +
            "        },\n" +
            "        -\n" +
            "        {\n" +
            "            -\n" +
            "            \"source\": {\n" +
            "                \"id\": null,\n" +
            "                \"name\": \"Startribune.com\"\n" +
            "            },\n" +
            "            \"author\": \"Lena H. Sun, Washington Post\",\n" +
            "            \"title\": \"U.S. health care worker with possible exposure to Ebola evacuated to Nebraska - Star Tribune\",\n" +
            "            \"description\": \"Follow the StarTribune for the news, photos and videos from the Twin Cities and beyond.\",\n" +
            "            \"url\": \"http://www.startribune.com/u-s-health-care-worker-with-possible-exposure-to-ebola-evacuated-to-nebraska/503670872/\",\n" +
            "            \"urlToImage\": \"http://stmedia.stimg.co/1546137412_10014102+Congo+Election.JPG?h=630&w=1200&fit=crop&bg=999&crop=faces\",\n" +
            "            \"publishedAt\": \"2018-12-30T02:36:56Z\",\n" +
            "            \"content\": \"An American health worker who was possibly exposed to Ebola while treating patients in the Democratic Republic of Congo was evacuated to the United States Saturday and placed in a secure area at the University of Nebraska Medical Center, hospital officials sa… [+3972 chars]\"\n" +
            "        },\n" +
            "        -\n" +
            "        {\n" +
            "            -\n" +
            "            \"source\": {\n" +
            "                \"id\": null,\n" +
            "                \"name\": \"Santamariatimes.com\"\n" +
            "            },\n" +
            "            \"author\": \"Gina Kim gkim@leecentralcoastnews.com\",\n" +
            "            \"title\": \"Suspect in Orcutt triple homicide identified as David McNabb, victims as Orcutt man and suspect's mother, sister - Santa Maria Times\",\n" +
            "            \"description\": \"Three people who were killed Friday night at a home in the 5900 block of Oakhill Road in Orcutt lived at the residence, and were apparently stabbed and beaten to\",\n" +
            "            \"url\": \"https://santamariatimes.com/news/local/crime-and-courts/suspect-in-orcutt-triple-homicide-identified-as-david-mcnabb-victims/article_47b840c6-0bda-11e9-996b-ef618ca47fab.html\",\n" +
            "            \"urlToImage\": \"https://bloximages.chicago2.vip.townnews.com/santamariatimes.com/content/tncms/assets/v3/editorial/4/7b/47b840c6-0bda-11e9-996b-ef618ca47fab/5c282cc68b722.preview.jpg?crop=1764%2C992%2C0%2C90&resize=1120%2C630&order=crop%2Cresize\",\n" +
            "            \"publishedAt\": \"2018-12-30T02:00:00Z\",\n" +
            "            \"content\": \"Three people who were killed Friday night at a home in the 5900 block of Oakhill Road in Orcutt lived at the residence, and were apparently stabbed and beaten to death by David Gerald McNabb, who also lived at the residence, a Santa Barbara County Sheriff's O… [+3584 chars]\"\n" +
            "        },\n" +
            "        -\n" +
            "        {\n" +
            "            -\n" +
            "            \"source\": {\n" +
            "                \"id\": \"cnn\",\n" +
            "                \"name\": \"CNN\"\n" +
            "            },\n" +
            "            \"author\": \"Dakin Andone, CNN\",\n" +
            "            \"title\": \"Bre Payton, 26-year-old conservative writer, dies following sudden illness - CNN\",\n" +
            "            \"description\": \"Bre Payton, a commentator and staff writer for The Federalist, died Friday in San Diego after a sudden illness, according to the conservative online magazine. She was 26.\",\n" +
            "            \"url\": \"https://www.cnn.com/2018/12/29/us/bre-payton-conservative-commentator-dies/index.html\",\n" +
            "            \"urlToImage\": \"https://cdn.cnn.com/cnnnext/dam/assets/181229100738-bre-payton-super-tease.jpg\",\n" +
            "            \"publishedAt\": \"2018-12-30T01:59:00Z\",\n" +
            "            \"content\": \"(CNN) Bre Payton, a commentator and staff writer for The Federalist, died Friday in San Diego after a sudden illness, according to the conservative online magazine. She was 26. Payton quickly became a featured commentator for conservative outlets after joinin… [+748 chars]\"\n" +
            "        },\n" +
            "        -\n" +
            "        {\n" +
            "            -\n" +
            "            \"source\": {\n" +
            "                \"id\": null,\n" +
            "                \"name\": \"Eonline.com\"\n" +
            "            },\n" +
            "            \"author\": \"Corinne Heller\",\n" +
            "            \"title\": \"Selena Gomez Is ''Excited'' for 2019 as She Focuses on Health - E! NEWS\",\n" +
            "            \"description\": \"The singer has had several health problems in recent years and spent time in treatment centers getting help for anxiety and depression this year.\",\n" +
            "            \"url\": \"https://www.eonline.com/news/1000109/selena-gomez-is-excited-for-the-new-year-as-she-focuses-on-her-health\",\n" +
            "            \"urlToImage\": \"https://akns-images.eonline.com/eol_images/Entire_Site/20181129/rs_600x600-181229074836-600.selena-gomez.ct.122918.jpg?fit=around|600:467&crop=600:467;center,top&output-quality=90\",\n" +
            "            \"publishedAt\": \"2018-12-29T23:10:00Z\",\n" +
            "            \"content\": \"Selena Gomez is looking forward to what 2019 will bring as she continues focusing on her health following years of turmoil. In 2015, the 26-year-old singer revealed that she has lupus and in 2017, she she underwent a kidney transplant. Earlier this year, Gome… [+774 chars]\"\n" +
            "        },\n" +
            "        -\n" +
            "        {\n" +
            "            -\n" +
            "            \"source\": {\n" +
            "                \"id\": null,\n" +
            "                \"name\": \"Forbes.com\"\n" +
            "            },\n" +
            "            \"author\": \"David Phelan\",\n" +
            "            \"title\": \"16 Best Gadgets Of The Year From Apple To Bose, Sonos To Sony - Forbes\",\n" +
            "            \"description\": \"As 2018 slips in 2019, take a look back at the best, cutest and shiniest tech developments of the year. I've chosen 16 outstanding gadgets across categories such as smartphones, wearables, laptops and sleep tech.\",\n" +
            "            \"url\": \"https://www.forbes.com/sites/davidphelan/2018/12/29/gadgets-of-the-year-the-16-best-of-2018-from-apple-to-sonos-bose-to-sony-iphone-xr-bose-sleepbuds-apple-watch-4/\",\n" +
            "            \"urlToImage\": \"https://thumbor.forbes.com/thumbor/600x315/https%3A%2F%2Fblogs-images.forbes.com%2Fdavidphelan%2Ffiles%2F2018%2F10%2FIMG_5155-1200x800.jpg\",\n" +
            "            \"publishedAt\": \"2018-12-29T22:00:00Z\",\n" +
            "            \"content\": \"As 2018 slips in 2019, take a look back at the best, cutest and shiniest tech developments of the year. Ive included links to the products Ive liked most. As always, these have been independently chosen and there are no affiliate links in any of them. Apple i… [+21956 chars]\"\n" +
            "        },\n" +
            "        -\n" +
            "        {\n" +
            "            -\n" +
            "            \"source\": {\n" +
            "                \"id\": null,\n" +
            "                \"name\": \"Washingtonexaminer.com\"\n" +
            "            },\n" +
            "            \"author\": \"https://www.washingtonexaminer.com/author/naomi-lim\",\n" +
            "            \"title\": \"Trump freezes federal employee pay - Washington Examiner\",\n" +
            "            \"description\": \"President Trump issued an executive order late Friday freezing the pay of about 1.5 million federal employees next year.\",\n" +
            "            \"url\": \"https://www.washingtonexaminer.com/news/trump-freezes-federal-employee-pay\",\n" +
            "            \"urlToImage\": \"https://mediadc.brightspotcdn.com/dims4/default/b724cb9/2147483647/strip/true/crop/2290x1202+0+0/resize/1200x630!/quality/90/?url=https%3A%2F%2Fmediadc.brightspotcdn.com%2Fc7%2Fab%2F56ff0da74e408fe7d86560d4fb20%2Fwhite-house-best-people.jpg\",\n" +
            "            \"publishedAt\": \"2018-12-29T21:57:00Z\",\n" +
            "            \"content\": \"P resident Trump issued an executive order late Friday freezing the pay of about 1.5 million federal employees next year. The order follows Trump indicating in August that he would block an automatic 2.1 percent raise for civilian workers who fall under the G… [+1753 chars]\"\n" +
            "        }\n" +
            "    ]\n" +
            "\n" +
            "}"

    @Test
    fun checkParsedJson() {
        try {
            val jsonObject = JSONObject(jsonOne.replace("-", ""))

            if(jsonObject[ResponseParser.STATUS] == ResponseParser.STATUS_CODE_ERROR)
                return

            val articles = try {
                jsonObject[ResponseParser.ARTICLES] as JSONArray
            } catch (e: JSONException) {
                JSONArray()
            }

            val articleEntities = ArrayList<ArticleEntity>()

            for (i in 0 until articles.length()) {
                val articleString = articles[i].toString()
                val article = JSONObject(articleString.replace("null", "\'\'"))

                articleEntities.add(ArticleEntity(
                        try { article[ResponseParser.AUTHOR] as String } catch (e: JSONException) { "" } ,
                        try { article[ResponseParser.TITLE] as String } catch (e: JSONException) { "" },
                        try { article[ResponseParser.DESCRIPTION] as String } catch (e: JSONException) { "" },
                        category,
                        try { article[ResponseParser.URL] as String } catch (e: JSONException) { "" },
                        try { article[ResponseParser.URL_TO_IMAGE] as String } catch (e: JSONException) { "" },
                        try { article[ResponseParser.PUBLISHED_AT] as String } catch (e: JSONException) { "" },
                        try { article[ResponseParser.CONTENT] as String } catch (e: JSONException) { "" }
                ))

                print("Hello World\n")
            }

            val code = try { jsonObject[ResponseParser.CODE] as String } catch (e: JSONException) { null }
            val message = try { jsonObject[ResponseParser.MESSAGE] as String } catch (e: JSONException) { null }

            print(ArticleResponseObject(
                    ResponseParser.STATUS_CODE_OK,
                    jsonObject[ResponseParser.TOTAL_RESULTS] as Int,
                    articleEntities,
                    code, message
            ))
        } catch (e: ClassCastException) {
            print(e.message)
        } catch (e: JSONException) {
            print(e.message)
        }
    }
}