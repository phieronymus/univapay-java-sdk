package com.univapay.sdk.errors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import com.univapay.sdk.UnivapaySDK;
import com.univapay.sdk.models.errors.UnivapayException;
import com.univapay.sdk.types.AuthType;
import com.univapay.sdk.utils.GenericTest;
import com.univapay.sdk.utils.MockRRGenerator;
import com.univapay.sdk.utils.mockcontent.ErrorsFakeRR;
import org.junit.Test;

public class ValidationErrorsTest extends GenericTest {

  @Test(expected = IllegalArgumentException.class)
  public void shouldParseValidationErrorsSuccessfully() throws IOException, UnivapayException {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse(
        "POST",
        "/authenticate",
        null,
        400,
        ErrorsFakeRR.invalidFormatFakeResponse,
        ErrorsFakeRR.invalidFormatFakeRequest);

    UnivapaySDK univapay = createTestInstance(AuthType.LOGIN_TOKEN);

    univapay.getLoginToken("newaccount", "c").build().dispatch();
  }

  @Test
  public void shouldHandleAnEmptyResponseBody() throws Exception {
    MockRRGenerator mockRRGenerator = new MockRRGenerator();
    mockRRGenerator.GenerateMockRequestResponse("GET", "/stores", null, 520, "{}");

    UnivapaySDK univapay = createTestInstance(AuthType.LOGIN_TOKEN);
    try {
      univapay.listStores().build().dispatch();
    } catch (UnivapayException e) {
      assertNull(e.getBody());
      assertEquals(e.getHttpStatusCode(), 520);
    }
  }
}