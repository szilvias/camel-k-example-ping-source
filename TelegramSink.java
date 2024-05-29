import org.apache.camel.builder.RouteBuilder;

public class TelegramSink extends RouteBuilder {
  @Override
  public void configure() throws Exception {
    from("knative:event/myData")
      .transform().simple("Received a ping event at ${date:now:yyyy-MM-dd HH:mm:ss}")
      .log("${body}")
      .to("telegram:bots?chatId=<your_chatID>&authorizationToken=<your_token>");
  }
}
