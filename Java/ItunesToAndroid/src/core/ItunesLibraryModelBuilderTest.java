package core;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class ItunesLibraryModelBuilderTest {

  private ItunesLibraryModelBuilder itunesLibraryModelBuilder;

  @Before
  public void setup() {
    itunesLibraryModelBuilder = new ItunesLibraryModelBuilder();
  }

  @Test
  public void asDate() {
    String dateAsString = "2013-11-22T20:02:41Z";

    Date asDate = itunesLibraryModelBuilder.asDate(dateAsString);

    assertThat(asDate, is(notNullValue()));
  }

  @Test
  public void asInt_bigInt() {
    String intAsString = "3509968257";

    assertThat(itunesLibraryModelBuilder.asInt(intAsString), is(notNullValue()));
  }

}
