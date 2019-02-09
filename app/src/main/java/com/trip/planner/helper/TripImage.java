package com.trip.planner.helper;


public class TripImage
{
    private String location;
    private String url;

    public TripImage(String location)
    {
        this.location = location;
    }

    public String getUrl()
    {
        switch (location)
        {
            case "Los Angeles":
                url = "https://www.thelagirl.com/wp-content/uploads/2017/01/shutterstock_386306728.jpg";
                break;
            case "Toronto":
                url = "https://cdn.images.express.co.uk/img/dynamic/25/590x/cn_tower_canada-372266.jpg";
                break;
            case "Iceland":
                url = "https://static.vinepair.com/wp-content/uploads/2017/06/iceland-summer-solstice-inside.jpg";
                break;
            case "San Francisco":
                url = "https://peiwc-test1445460803.netdna-ssl.com/wp-content/uploads/2016/02/san-francisco-is-americas-snobbiest-city-according-to-the-rest-of-the-country.jpg";
                break;
            case "Las Vegas":
                url = "https://static1.squarespace.com/static/5813cddb8419c25c3b42eacd/593d9408cd0f68786f401768/5970d6588419c275e8751a3f/1537833391350/?format=1000w";
                break;
            case "Florida":
                url = "https://wallimpex.com/data/out/285/miami-wallpaper-6648975.jpg";
                break;
            case "Orlando":
                url = "https://www.thetopvillas.com/blog/wp-content/uploads/2017/01/what-is-orlando-known-for.jpg";
                break;
            case "Disney World":
            case "Disneyworld":
                url = "https://secure.cdn1.wdpromedia.com/dam/wdpro-assets/home/hpr/walt-disney-world-six-parks-UK-video-sq.jpg";
                break;
            case "Disneyland":
            case "Disney Land":
                url = "https://secure.cdn1.wdpromedia.com/dam/wdpro-assets/home/hpr/walt-disney-world-six-parks-UK-video-sq.jpg";
                break;
            case "Miami":
                url = "http://1.bp.blogspot.com/-n7dAQbDS1TU/UbzcKjrogNI/AAAAAAAAExI/RHl5s6Ioap8/s640/key_west_island_beach.jpg";
                break;
            case "New York":
                url = "https://www.burgessyachts.com/media/adminforms/locations/n/e/new_york_1.jpg";
                break;
            case "New York City":
                url = "https://www.tripsavvy.com/thmb/b_2t99EgDyTJU5pFZWD921k2nZc=/960x0/filters:no_upscale():max_bytes(150000):strip_icc()/GettyImages-555749235-5959b9845f9b58843f4526a5.jpg";
                break;
            case "Arizona":
                url = "https://cdn.beam.usnews.com/dims4/USNEWS/c54d927/2147483647/thumbnail/1000x468/quality/90/?url=http%3A%2F%2Fcom-usnews-beam-media.s3.amazonaws.com%2F6d%2Ff7%2F217f001f40069b5b73512047526a%2F170207-editorial.jpg";
                break;
            case "Chicago":
                url = "https://i1.wp.com/farm7.staticflickr.com/6149/6007844020_ff76627b3b_b.jpg";
                break;
            case "Dhaka":
                url = "https://c1.staticflickr.com/4/3871/14696333789_181ea6dae7_b.jpg";
                break;
            case "Bangladesh":
                url = "https://upload.wikimedia.org/wikipedia/commons/b/b2/Beautiful_Village_in_Bangladesh.jpg";
                break;
            case "Paris":
                url = "https://cdn.fodors.com/wp-content/uploads/2018/10/HERO_UltimateParis_Heroshutterstock_112137761.jpg";
                break;
            case "Cuba":
            case "Varadero":
                url = "https://i.ytimg.com/vi/N4ZMYokojGw/maxresdefault.jpg";
                break;
            case "India":
                url = "https://www.ivisa.com/images/blog/india-image-one.jpg";
                break;
            case "Dominican Republic":
            case "Punta Cana":
                url = "https://media-cdn.tripadvisor.com/media/photo-s/14/81/7f/5d/pool.jpg";
                break;
            case "Venice":
                url = "https://cdn-image.departures.com/sites/default/files/styles/responsive_900x600/public/1527108916/venice-italy-gondola-canal-VENICEGUIDE0518.jpg?itok=9z1FExRK";
                break;
            case "Rome":
                url = "https://www.hellomagazine.com/imagenes/travel/2018061449462/things-to-do-in-rome-3-days/0-243-749/Colosseum-Rome-t.jpg";
                break;
            case "Milan":
                url = "http://e-sushi.fr/imagearticle/2015/07/MilanCathedral3.jpg";
                break;
            case "Florence":
                url = "https://lonelyplanetwp.imgix.net/2018/02/palazzo-vecchio-florence-a48886909a0c.jpg?crop=entropy&fit=crop&h=421&sharp=10&vib=20&w=748";
                break;
            case "Naples":
                url = "https://cdn2.veltra.com/ptr/20170220030536_673632069_13312_0.jpg?imwidth=550&impolicy=custom";
                break;
            case "Nova Scotia":
            case "Halifax":
                url = "https://data.jigsawpuzzle.co.uk/castorland.21/the-old-town-of-stockholm-sweden-jigsaw-puzzle-500-pieces.61463-1.fs.jpg";
                break;
            case "Alberta":
            case "Lake Louise":
            case "Banff":
                url = "https://handluggageonly.co.uk/wp-content/uploads/2016/09/Hand-Luggage-Only-5.jpg";
                break;
            case "British Columbia":
            case "Vancouver":
                url = "https://assets.simpleviewcms.com/simpleview/image/upload/c_fill,h_361,q_60,w_641/v1/clients/vancouverbc/amazing_vancouver_video_still_a156a786-54dd-45ea-941c-41d6a04e2212.jpg";
                break;
            case "Greece":
            case "Athens":
                url = "https://www.gannett-cdn.com/-mm-/1abbac059a7e6f21ff3aa7e38760a41a48819119/c=0-217-2118-1414/local/-/media/2018/04/14/USATODAY/USATODAY/636592630826477551-GettyImages-813272528.jpg?width=3200&height=1680&fit=crop";
                break;
            case "Switzerland":
            case "Zurich":
                url = "http://www.lonelyplanet.com/travel-blog/tip-article/wordpress_uploads/2017/08/shutterstock_611229743-bde2e023e33c.jpg";
                break;
            case "Spain":
                url = "https://kids.nationalgeographic.com/content/dam/kids/photos/Countries/Q-Z/spain-madrid.ngsversion.1396531799117.adapt.1900.1.jpg";
                break;
            case "Barcelona":
                url = "https://www.odysseytraveller.com/app/uploads/2017/11/Barcelona-Spain-iStock-619257048.jpg";
                break;
            case "Madrid":
                url = "https://images.lucasfox.com/location/4x3_960w/B427B4AFB9.jpg";
                break;
            case "Bangkok":
                url = "https://www.roadaffair.com/wp-content/uploads/2017/10/bangkok-thailand-shutterstock_300284237.jpg";
                break;
            case "Thailand":
                url = "https://www.rei.com/adventures/assets/adventures/images/trip/core/asia/fta_hero";
                break;
            case "Singapore":
                url = "https://thumbor.forbes.com/thumbor/1280x868/https%3A%2F%2Fblogs-images.forbes.com%2Falexcapri%2Ffiles%2F2018%2F09%2FSingapore-1200x800.jpg";
                break;

            default:
                url="https://img.freepik.com/free-vector/airplane-holidays-travel-with-world-monuments_23-2147491300.jpg?size=338&ext=jpg&ve=2";
        }
        return url;
    }


}
