package com.hiteware.olympics;

import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ParserTest {
    String testData = "<!-- Generated HTML --><html><head><title>Now Playing</title><link rel=\"stylesheet\" href=\"http://192.168.50.146:80/style.css\" type=\"text/css\" media=\"all\"><link rel=\"alternate\" type=\"text/xml\" title=\"RSS 2.0\" href=\"http://192.168.50.146:80/rss/nowplaying.xml\"><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"></head><body><img src=\"http://192.168.50.146:80/images/tivodance.gif\" align=\"right\"><h1>Now Playing</h1><table cellpadding=\"7\" width=\"100%\"><tr bgcolor=\"E5E5C5\"><th width=\"1%\"><th width=\"1%\">Source</th><th>Description</th><th width=\"5%\">Date</th><th width=\"5%\">Size</th><th width=\"5%\">Links</th></tr><tr bgcolor=\"F5F595\"><td><td align=\"center\" valign=\"top\"><img src=\"http://192.168.50.146:80/ChannelLogo/logo-66500.png\" alt=\"FXXHD\"></td><td align=\"left\" valign=\"top\"><b>After Earth</b><br>With his father trapped in the wreckage of their spacecraft, a youth treks across Earth's now-hostile terrain to recover their rescue beacon and signal for help. Copyright Tribune Media Services, Inc.</td><td align=\"center\" valign=\"top\" nowrap>Wed<br>4/27</td><td align=\"center\" valign=\"top\" nowrap>1:59:55<br>7.86 GB</td><td align=\"center\" valign=\"top\" nowrap><i>Protected</i></td></tr><tr bgcolor=\"F5F5B5\"><td align=\"center\" valign=\"top\"><img src=\"http://192.168.50.146:80/images/folder.png\"></td><td><td valign=\"top\"><b>The Outer Limits</b></td><td align=\"center\" valign=\"top\" nowrap>Wed<br>4/27</td><td align=\"center\" valign=\"top\">16 items<br></td><td align=\"center\" valign=\"top\"><a href=\"TiVoConnect?Command=QueryContainer&amp;Container=%2FNowPlaying%2F17%2F17030\">folder</a></td></tr><tr bgcolor=\"F5F595\"><td align=\"center\" valign=\"top\"><img src=\"http://192.168.50.146:80/images/folder.png\"></td><td><td valign=\"top\"><b>A Chef's Life</b></td><td align=\"center\" valign=\"top\" nowrap>Wed<br>4/27</td><td align=\"center\" valign=\"top\">23 items<br></td><td align=\"center\" valign=\"top\"><a href=\"TiVoConnect?Command=QueryContainer&amp;Container=%2FNowPlaying%2F17%2F279030631\">folder</a></td></tr><tr bgcolor=\"F5F5B5\"><td><td align=\"center\" valign=\"top\"><img src=\"http://192.168.50.146:80/ChannelLogo/logo-66500.png\" alt=\"FXXHD\"></td><td align=\"left\" valign=\"top\"><b>The Simpsons: &quot;The Bart of War&quot;</b><br>When Homer and Marge enroll Bart in an activities club, he wages war against Milhouse, who belongs to a different one. Copyright Tribune Media Services, Inc.</td><td align=\"center\" valign=\"top\" nowrap>Wed<br>4/27</td><td align=\"center\" valign=\"top\" nowrap>0:30:01<br>1.97 GB</td><td align=\"center\" valign=\"top\" nowrap><i>Protected</i></td></tr><tr bgcolor=\"F5F595\"><td align=\"center\" valign=\"top\"><img src=\"http://192.168.50.146:80/images/folder.png\"></td><td><td valign=\"top\"><b>Kitchen Nightmares</b></td><td align=\"center\" valign=\"top\" nowrap>Mon<br>4/25</td><td align=\"center\" valign=\"top\">2 items<br></td><td align=\"center\" valign=\"top\"><a href=\"TiVoConnect?Command=QueryContainer&amp;Container=%2FNowPlaying%2F17%2F97868152\">folder</a></td></tr></table>18 items, <a href=\"index.html?Recurse=Yes\">classic</a>.<p><font size=\"-2\">This feature is not supported. The TiVo license agreement allows you to transfer content to up to ten devices within your household, but not outside your household.  Unauthorized transfers or distribution of copyrighted works outside of your home may constitute a copyright infringement. TiVo reserves the right to terminate the TiVo service accounts of users who transfer or distribute content in violation of this Agreement. </font></body></html>";



    @Test
    public void canParseTitles() throws Exception {
//    After Earth
//    The Outer Limits
//    A Chef's Life
//    The Simpsons: &quot;The Bart of War&quot;
//    Kitchen Nightmares

        parser parser = new parser();
        parser.parse(testData);
        ArrayList<Recording> recordings = parser.getRecordings();
        assertThat(recordings.get(0).getTitle(), equalTo("After Earth"));
        assertThat(recordings.get(1).getTitle(), equalTo("The Outer Limits"));
    }
}