package environment;


import ru.qatools.properties.Property;
import ru.qatools.properties.PropertyLoader;
import ru.qatools.properties.Resource;

@Resource.Classpath("config.properties")
public interface Configuration {
    Configuration CONFIGURATION = PropertyLoader.newInstance().populate(Configuration.class);

    @Property("host")
    String getHost();

    @Property("currency.gbp")
    String getCurrencyGbp();

    @Property("currency.usd")
    String getCurrencyUsd();

    @Property("currency.eur")
    String getCurrencyEur();

    @Property("currency.pln")
    String getCurrencyPln();

    @Property("currency.yen")
    String getCurrencyYen();

    @Property("exchange.rate.usd.gbp")
    Double getExchangeRateUsdToGbp();

    @Property("exchange.rate.eur.gbp")
    Double getExchangeRateEurToGbp();

    @Property("exchange.rate.pln.gbp")
    Double getExchangeRatePlnToGbp();

    @Property("exchange.rate.yen.gbp")
    Double getExchangeRateYenToGbp();
}
