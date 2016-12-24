package test.numbers;

import static main.matcher.BasicMatchers.containsExactlyAll;
import static main.matcher.BasicMatchers.empty;
import static main.matcher.BasicMatchers.is;
import static main.numbers.InfiniteNaturalNumber.ELEVEN;
import static main.numbers.InfiniteNaturalNumber.FIVE;
import static main.numbers.InfiniteNaturalNumber.FOUR;
import static main.numbers.InfiniteNaturalNumber.ONE;
import static main.numbers.InfiniteNaturalNumber.SEVEN;
import static main.numbers.InfiniteNaturalNumber.THREE;
import static main.numbers.InfiniteNaturalNumber.TWELVE;
import static main.numbers.InfiniteNaturalNumber.TWO;
import static main.numbers.InfiniteNaturalNumber.ZERO;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.bechte.junit.runners.context.HierarchicalContextRunner;
import main.junit.PerfTestScenario;
import main.junit.Scenario;
import main.numbers.InfiniteNaturalNumber;
import main.numbers.PrimeNumbersCalculator;
import main.util.CollectionUtils;
import main.util.FormatterUtils;

@RunWith(HierarchicalContextRunner.class)
public class PrimeNumbersCalculatorTest {

	public class GetNextPrimeNumber extends Scenario {

		private InfiniteNaturalNumber expectedPrimeNumber;
		private List<InfiniteNaturalNumber> previousPrimeNumbers;

		protected void computeAndCheck() {
			InfiniteNaturalNumber foundNextPrimeNumber = PrimeNumbersCalculator
					.getNextPrimeNumber(previousPrimeNumbers);

			assertThat(foundNextPrimeNumber, is(expectedPrimeNumber));
		}

		@After
		public void after() {
			computeAndCheck();
		}

		public class TestResult extends Scenario {
			@Test
			public void after_nothing_is_2() {
				previousPrimeNumbers = CollectionUtils.emptyList();
				expectedPrimeNumber = TWO;
			}

			@Test
			public void after_2_is_3() {
				previousPrimeNumbers = CollectionUtils.asList(TWO);
				expectedPrimeNumber = THREE;
			}

			@Test
			public void after_2_3_is_5() {
				previousPrimeNumbers = CollectionUtils.asList(TWO, THREE);
				expectedPrimeNumber = FIVE;
			}

			@Test
			public void after_2_3_5_is_7() {
				previousPrimeNumbers = CollectionUtils.asList(TWO, THREE, FIVE);
				expectedPrimeNumber = SEVEN;
			}

			@Ignore
			@Test
			public void called_four_times() {
				previousPrimeNumbers = CollectionUtils.emptyList();
				previousPrimeNumbers = CollectionUtils.asList(
						PrimeNumbersCalculator.getNextPrimeNumber(previousPrimeNumbers),
						PrimeNumbersCalculator.getNextPrimeNumber(previousPrimeNumbers),
						PrimeNumbersCalculator.getNextPrimeNumber(previousPrimeNumbers));
				expectedPrimeNumber = ELEVEN;
			}
		}

		public class PerfTest extends PerfTestScenario {
			@After
			public void after() {
				computeAndCheck();
				System.out.println("GetNextPrimeNumber after " + previousPrimeNumbers + " was calculated in "
						+ FormatterUtils.GetDurationAsString(getTestDuration()));
			}

			@Test
			public void after_2_until_997_is_1009() {
				previousPrimeNumbers = CollectionUtils.asList(new InfiniteNaturalNumber("2"),
						new InfiniteNaturalNumber("3"), new InfiniteNaturalNumber("5"), new InfiniteNaturalNumber("7"),
						new InfiniteNaturalNumber("11"), new InfiniteNaturalNumber("13"),
						new InfiniteNaturalNumber("17"), new InfiniteNaturalNumber("19"),
						new InfiniteNaturalNumber("23"), new InfiniteNaturalNumber("29"),
						new InfiniteNaturalNumber("31"), new InfiniteNaturalNumber("37"),
						new InfiniteNaturalNumber("41"), new InfiniteNaturalNumber("43"),
						new InfiniteNaturalNumber("47"), new InfiniteNaturalNumber("53"),
						new InfiniteNaturalNumber("59"), new InfiniteNaturalNumber("61"),
						new InfiniteNaturalNumber("67"), new InfiniteNaturalNumber("71"),
						new InfiniteNaturalNumber("73"), new InfiniteNaturalNumber("79"),
						new InfiniteNaturalNumber("83"), new InfiniteNaturalNumber("89"),
						new InfiniteNaturalNumber("97"), new InfiniteNaturalNumber("101"),
						new InfiniteNaturalNumber("103"), new InfiniteNaturalNumber("107"),
						new InfiniteNaturalNumber("109"), new InfiniteNaturalNumber("113"),
						new InfiniteNaturalNumber("127"), new InfiniteNaturalNumber("131"),
						new InfiniteNaturalNumber("137"), new InfiniteNaturalNumber("139"),
						new InfiniteNaturalNumber("149"), new InfiniteNaturalNumber("151"),
						new InfiniteNaturalNumber("157"), new InfiniteNaturalNumber("163"),
						new InfiniteNaturalNumber("167"), new InfiniteNaturalNumber("173"),
						new InfiniteNaturalNumber("179"), new InfiniteNaturalNumber("181"),
						new InfiniteNaturalNumber("191"), new InfiniteNaturalNumber("193"),
						new InfiniteNaturalNumber("197"), new InfiniteNaturalNumber("199"),
						new InfiniteNaturalNumber("211"), new InfiniteNaturalNumber("223"),
						new InfiniteNaturalNumber("227"), new InfiniteNaturalNumber("229"),
						new InfiniteNaturalNumber("233"), new InfiniteNaturalNumber("239"),
						new InfiniteNaturalNumber("241"), new InfiniteNaturalNumber("251"),
						new InfiniteNaturalNumber("257"), new InfiniteNaturalNumber("263"),
						new InfiniteNaturalNumber("269"), new InfiniteNaturalNumber("271"),
						new InfiniteNaturalNumber("277"), new InfiniteNaturalNumber("281"),
						new InfiniteNaturalNumber("283"), new InfiniteNaturalNumber("293"),
						new InfiniteNaturalNumber("307"), new InfiniteNaturalNumber("311"),
						new InfiniteNaturalNumber("313"), new InfiniteNaturalNumber("317"),
						new InfiniteNaturalNumber("331"), new InfiniteNaturalNumber("337"),
						new InfiniteNaturalNumber("347"), new InfiniteNaturalNumber("349"),
						new InfiniteNaturalNumber("353"), new InfiniteNaturalNumber("359"),
						new InfiniteNaturalNumber("367"), new InfiniteNaturalNumber("373"),
						new InfiniteNaturalNumber("379"), new InfiniteNaturalNumber("383"),
						new InfiniteNaturalNumber("389"), new InfiniteNaturalNumber("397"),
						new InfiniteNaturalNumber("401"), new InfiniteNaturalNumber("409"),
						new InfiniteNaturalNumber("419"), new InfiniteNaturalNumber("421"),
						new InfiniteNaturalNumber("431"), new InfiniteNaturalNumber("433"),
						new InfiniteNaturalNumber("439"), new InfiniteNaturalNumber("443"),
						new InfiniteNaturalNumber("449"), new InfiniteNaturalNumber("457"),
						new InfiniteNaturalNumber("461"), new InfiniteNaturalNumber("463"),
						new InfiniteNaturalNumber("467"), new InfiniteNaturalNumber("479"),
						new InfiniteNaturalNumber("487"), new InfiniteNaturalNumber("491"),
						new InfiniteNaturalNumber("499"), new InfiniteNaturalNumber("503"),
						new InfiniteNaturalNumber("509"), new InfiniteNaturalNumber("521"),
						new InfiniteNaturalNumber("523"), new InfiniteNaturalNumber("541"),
						new InfiniteNaturalNumber("547"), new InfiniteNaturalNumber("557"),
						new InfiniteNaturalNumber("563"), new InfiniteNaturalNumber("569"),
						new InfiniteNaturalNumber("571"), new InfiniteNaturalNumber("577"),
						new InfiniteNaturalNumber("587"), new InfiniteNaturalNumber("593"),
						new InfiniteNaturalNumber("599"), new InfiniteNaturalNumber("601"),
						new InfiniteNaturalNumber("607"), new InfiniteNaturalNumber("613"),
						new InfiniteNaturalNumber("617"), new InfiniteNaturalNumber("619"),
						new InfiniteNaturalNumber("631"), new InfiniteNaturalNumber("641"),
						new InfiniteNaturalNumber("643"), new InfiniteNaturalNumber("647"),
						new InfiniteNaturalNumber("653"), new InfiniteNaturalNumber("659"),
						new InfiniteNaturalNumber("661"), new InfiniteNaturalNumber("673"),
						new InfiniteNaturalNumber("677"), new InfiniteNaturalNumber("683"),
						new InfiniteNaturalNumber("691"), new InfiniteNaturalNumber("701"),
						new InfiniteNaturalNumber("709"), new InfiniteNaturalNumber("719"),
						new InfiniteNaturalNumber("727"), new InfiniteNaturalNumber("733"),
						new InfiniteNaturalNumber("739"), new InfiniteNaturalNumber("743"),
						new InfiniteNaturalNumber("751"), new InfiniteNaturalNumber("757"),
						new InfiniteNaturalNumber("761"), new InfiniteNaturalNumber("769"),
						new InfiniteNaturalNumber("773"), new InfiniteNaturalNumber("787"),
						new InfiniteNaturalNumber("797"), new InfiniteNaturalNumber("809"),
						new InfiniteNaturalNumber("811"), new InfiniteNaturalNumber("821"),
						new InfiniteNaturalNumber("823"), new InfiniteNaturalNumber("827"),
						new InfiniteNaturalNumber("829"), new InfiniteNaturalNumber("839"),
						new InfiniteNaturalNumber("853"), new InfiniteNaturalNumber("857"),
						new InfiniteNaturalNumber("859"), new InfiniteNaturalNumber("863"),
						new InfiniteNaturalNumber("877"), new InfiniteNaturalNumber("881"),
						new InfiniteNaturalNumber("883"), new InfiniteNaturalNumber("887"),
						new InfiniteNaturalNumber("907"), new InfiniteNaturalNumber("911"),
						new InfiniteNaturalNumber("919"), new InfiniteNaturalNumber("929"),
						new InfiniteNaturalNumber("937"), new InfiniteNaturalNumber("941"),
						new InfiniteNaturalNumber("947"), new InfiniteNaturalNumber("953"),
						new InfiniteNaturalNumber("967"), new InfiniteNaturalNumber("971"),
						new InfiniteNaturalNumber("977"), new InfiniteNaturalNumber("983"),
						new InfiniteNaturalNumber("991"), new InfiniteNaturalNumber("997"));
				expectedPrimeNumber = new InfiniteNaturalNumber("1009");
			}

			@Test
			public void after_2_until_1999_is_2003() {
				previousPrimeNumbers = CollectionUtils.asList(new InfiniteNaturalNumber("2"),
						new InfiniteNaturalNumber("3"), new InfiniteNaturalNumber("5"), new InfiniteNaturalNumber("7"),
						new InfiniteNaturalNumber("11"), new InfiniteNaturalNumber("13"),
						new InfiniteNaturalNumber("17"), new InfiniteNaturalNumber("19"),
						new InfiniteNaturalNumber("23"), new InfiniteNaturalNumber("29"),
						new InfiniteNaturalNumber("31"), new InfiniteNaturalNumber("37"),
						new InfiniteNaturalNumber("41"), new InfiniteNaturalNumber("43"),
						new InfiniteNaturalNumber("47"), new InfiniteNaturalNumber("53"),
						new InfiniteNaturalNumber("59"), new InfiniteNaturalNumber("61"),
						new InfiniteNaturalNumber("67"), new InfiniteNaturalNumber("71"),
						new InfiniteNaturalNumber("73"), new InfiniteNaturalNumber("79"),
						new InfiniteNaturalNumber("83"), new InfiniteNaturalNumber("89"),
						new InfiniteNaturalNumber("97"), new InfiniteNaturalNumber("101"),
						new InfiniteNaturalNumber("103"), new InfiniteNaturalNumber("107"),
						new InfiniteNaturalNumber("109"), new InfiniteNaturalNumber("113"),
						new InfiniteNaturalNumber("127"), new InfiniteNaturalNumber("131"),
						new InfiniteNaturalNumber("137"), new InfiniteNaturalNumber("139"),
						new InfiniteNaturalNumber("149"), new InfiniteNaturalNumber("151"),
						new InfiniteNaturalNumber("157"), new InfiniteNaturalNumber("163"),
						new InfiniteNaturalNumber("167"), new InfiniteNaturalNumber("173"),
						new InfiniteNaturalNumber("179"), new InfiniteNaturalNumber("181"),
						new InfiniteNaturalNumber("191"), new InfiniteNaturalNumber("193"),
						new InfiniteNaturalNumber("197"), new InfiniteNaturalNumber("199"),
						new InfiniteNaturalNumber("211"), new InfiniteNaturalNumber("223"),
						new InfiniteNaturalNumber("227"), new InfiniteNaturalNumber("229"),
						new InfiniteNaturalNumber("233"), new InfiniteNaturalNumber("239"),
						new InfiniteNaturalNumber("241"), new InfiniteNaturalNumber("251"),
						new InfiniteNaturalNumber("257"), new InfiniteNaturalNumber("263"),
						new InfiniteNaturalNumber("269"), new InfiniteNaturalNumber("271"),
						new InfiniteNaturalNumber("277"), new InfiniteNaturalNumber("281"),
						new InfiniteNaturalNumber("283"), new InfiniteNaturalNumber("293"),
						new InfiniteNaturalNumber("307"), new InfiniteNaturalNumber("311"),
						new InfiniteNaturalNumber("313"), new InfiniteNaturalNumber("317"),
						new InfiniteNaturalNumber("331"), new InfiniteNaturalNumber("337"),
						new InfiniteNaturalNumber("347"), new InfiniteNaturalNumber("349"),
						new InfiniteNaturalNumber("353"), new InfiniteNaturalNumber("359"),
						new InfiniteNaturalNumber("367"), new InfiniteNaturalNumber("373"),
						new InfiniteNaturalNumber("379"), new InfiniteNaturalNumber("383"),
						new InfiniteNaturalNumber("389"), new InfiniteNaturalNumber("397"),
						new InfiniteNaturalNumber("401"), new InfiniteNaturalNumber("409"),
						new InfiniteNaturalNumber("419"), new InfiniteNaturalNumber("421"),
						new InfiniteNaturalNumber("431"), new InfiniteNaturalNumber("433"),
						new InfiniteNaturalNumber("439"), new InfiniteNaturalNumber("443"),
						new InfiniteNaturalNumber("449"), new InfiniteNaturalNumber("457"),
						new InfiniteNaturalNumber("461"), new InfiniteNaturalNumber("463"),
						new InfiniteNaturalNumber("467"), new InfiniteNaturalNumber("479"),
						new InfiniteNaturalNumber("487"), new InfiniteNaturalNumber("491"),
						new InfiniteNaturalNumber("499"), new InfiniteNaturalNumber("503"),
						new InfiniteNaturalNumber("509"), new InfiniteNaturalNumber("521"),
						new InfiniteNaturalNumber("523"), new InfiniteNaturalNumber("541"),
						new InfiniteNaturalNumber("547"), new InfiniteNaturalNumber("557"),
						new InfiniteNaturalNumber("563"), new InfiniteNaturalNumber("569"),
						new InfiniteNaturalNumber("571"), new InfiniteNaturalNumber("577"),
						new InfiniteNaturalNumber("587"), new InfiniteNaturalNumber("593"),
						new InfiniteNaturalNumber("599"), new InfiniteNaturalNumber("601"),
						new InfiniteNaturalNumber("607"), new InfiniteNaturalNumber("613"),
						new InfiniteNaturalNumber("617"), new InfiniteNaturalNumber("619"),
						new InfiniteNaturalNumber("631"), new InfiniteNaturalNumber("641"),
						new InfiniteNaturalNumber("643"), new InfiniteNaturalNumber("647"),
						new InfiniteNaturalNumber("653"), new InfiniteNaturalNumber("659"),
						new InfiniteNaturalNumber("661"), new InfiniteNaturalNumber("673"),
						new InfiniteNaturalNumber("677"), new InfiniteNaturalNumber("683"),
						new InfiniteNaturalNumber("691"), new InfiniteNaturalNumber("701"),
						new InfiniteNaturalNumber("709"), new InfiniteNaturalNumber("719"),
						new InfiniteNaturalNumber("727"), new InfiniteNaturalNumber("733"),
						new InfiniteNaturalNumber("739"), new InfiniteNaturalNumber("743"),
						new InfiniteNaturalNumber("751"), new InfiniteNaturalNumber("757"),
						new InfiniteNaturalNumber("761"), new InfiniteNaturalNumber("769"),
						new InfiniteNaturalNumber("773"), new InfiniteNaturalNumber("787"),
						new InfiniteNaturalNumber("797"), new InfiniteNaturalNumber("809"),
						new InfiniteNaturalNumber("811"), new InfiniteNaturalNumber("821"),
						new InfiniteNaturalNumber("823"), new InfiniteNaturalNumber("827"),
						new InfiniteNaturalNumber("829"), new InfiniteNaturalNumber("839"),
						new InfiniteNaturalNumber("853"), new InfiniteNaturalNumber("857"),
						new InfiniteNaturalNumber("859"), new InfiniteNaturalNumber("863"),
						new InfiniteNaturalNumber("877"), new InfiniteNaturalNumber("881"),
						new InfiniteNaturalNumber("883"), new InfiniteNaturalNumber("887"),
						new InfiniteNaturalNumber("907"), new InfiniteNaturalNumber("911"),
						new InfiniteNaturalNumber("919"), new InfiniteNaturalNumber("929"),
						new InfiniteNaturalNumber("937"), new InfiniteNaturalNumber("941"),
						new InfiniteNaturalNumber("947"), new InfiniteNaturalNumber("953"),
						new InfiniteNaturalNumber("967"), new InfiniteNaturalNumber("971"),
						new InfiniteNaturalNumber("977"), new InfiniteNaturalNumber("983"),
						new InfiniteNaturalNumber("991"), new InfiniteNaturalNumber("997"),
						new InfiniteNaturalNumber("1009"), new InfiniteNaturalNumber("1013"),
						new InfiniteNaturalNumber("1019"), new InfiniteNaturalNumber("1021"),
						new InfiniteNaturalNumber("1031"), new InfiniteNaturalNumber("1033"),
						new InfiniteNaturalNumber("1039"), new InfiniteNaturalNumber("1049"),
						new InfiniteNaturalNumber("1051"), new InfiniteNaturalNumber("1061"),
						new InfiniteNaturalNumber("1063"), new InfiniteNaturalNumber("1069"),
						new InfiniteNaturalNumber("1087"), new InfiniteNaturalNumber("1091"),
						new InfiniteNaturalNumber("1093"), new InfiniteNaturalNumber("1097"),
						new InfiniteNaturalNumber("1103"), new InfiniteNaturalNumber("1109"),
						new InfiniteNaturalNumber("1117"), new InfiniteNaturalNumber("1123"),
						new InfiniteNaturalNumber("1129"), new InfiniteNaturalNumber("1151"),
						new InfiniteNaturalNumber("1153"), new InfiniteNaturalNumber("1163"),
						new InfiniteNaturalNumber("1171"), new InfiniteNaturalNumber("1181"),
						new InfiniteNaturalNumber("1187"), new InfiniteNaturalNumber("1193"),
						new InfiniteNaturalNumber("1201"), new InfiniteNaturalNumber("1213"),
						new InfiniteNaturalNumber("1217"), new InfiniteNaturalNumber("1223"),
						new InfiniteNaturalNumber("1229"), new InfiniteNaturalNumber("1231"),
						new InfiniteNaturalNumber("1237"), new InfiniteNaturalNumber("1249"),
						new InfiniteNaturalNumber("1259"), new InfiniteNaturalNumber("1277"),
						new InfiniteNaturalNumber("1279"), new InfiniteNaturalNumber("1283"),
						new InfiniteNaturalNumber("1289"), new InfiniteNaturalNumber("1291"),
						new InfiniteNaturalNumber("1297"), new InfiniteNaturalNumber("1301"),
						new InfiniteNaturalNumber("1303"), new InfiniteNaturalNumber("1307"),
						new InfiniteNaturalNumber("1319"), new InfiniteNaturalNumber("1321"),
						new InfiniteNaturalNumber("1327"), new InfiniteNaturalNumber("1361"),
						new InfiniteNaturalNumber("1367"), new InfiniteNaturalNumber("1373"),
						new InfiniteNaturalNumber("1381"), new InfiniteNaturalNumber("1399"),
						new InfiniteNaturalNumber("1409"), new InfiniteNaturalNumber("1423"),
						new InfiniteNaturalNumber("1427"), new InfiniteNaturalNumber("1429"),
						new InfiniteNaturalNumber("1433"), new InfiniteNaturalNumber("1439"),
						new InfiniteNaturalNumber("1447"), new InfiniteNaturalNumber("1451"),
						new InfiniteNaturalNumber("1453"), new InfiniteNaturalNumber("1459"),
						new InfiniteNaturalNumber("1471"), new InfiniteNaturalNumber("1481"),
						new InfiniteNaturalNumber("1483"), new InfiniteNaturalNumber("1487"),
						new InfiniteNaturalNumber("1489"), new InfiniteNaturalNumber("1493"),
						new InfiniteNaturalNumber("1499"), new InfiniteNaturalNumber("1511"),
						new InfiniteNaturalNumber("1523"), new InfiniteNaturalNumber("1531"),
						new InfiniteNaturalNumber("1543"), new InfiniteNaturalNumber("1549"),
						new InfiniteNaturalNumber("1553"), new InfiniteNaturalNumber("1559"),
						new InfiniteNaturalNumber("1567"), new InfiniteNaturalNumber("1571"),
						new InfiniteNaturalNumber("1579"), new InfiniteNaturalNumber("1583"),
						new InfiniteNaturalNumber("1597"), new InfiniteNaturalNumber("1601"),
						new InfiniteNaturalNumber("1607"), new InfiniteNaturalNumber("1609"),
						new InfiniteNaturalNumber("1613"), new InfiniteNaturalNumber("1619"),
						new InfiniteNaturalNumber("1621"), new InfiniteNaturalNumber("1627"),
						new InfiniteNaturalNumber("1637"), new InfiniteNaturalNumber("1657"),
						new InfiniteNaturalNumber("1663"), new InfiniteNaturalNumber("1667"),
						new InfiniteNaturalNumber("1669"), new InfiniteNaturalNumber("1693"),
						new InfiniteNaturalNumber("1697"), new InfiniteNaturalNumber("1699"),
						new InfiniteNaturalNumber("1709"), new InfiniteNaturalNumber("1721"),
						new InfiniteNaturalNumber("1723"), new InfiniteNaturalNumber("1733"),
						new InfiniteNaturalNumber("1741"), new InfiniteNaturalNumber("1747"),
						new InfiniteNaturalNumber("1753"), new InfiniteNaturalNumber("1759"),
						new InfiniteNaturalNumber("1777"), new InfiniteNaturalNumber("1783"),
						new InfiniteNaturalNumber("1787"), new InfiniteNaturalNumber("1789"),
						new InfiniteNaturalNumber("1801"), new InfiniteNaturalNumber("1811"),
						new InfiniteNaturalNumber("1823"), new InfiniteNaturalNumber("1831"),
						new InfiniteNaturalNumber("1847"), new InfiniteNaturalNumber("1861"),
						new InfiniteNaturalNumber("1867"), new InfiniteNaturalNumber("1871"),
						new InfiniteNaturalNumber("1873"), new InfiniteNaturalNumber("1877"),
						new InfiniteNaturalNumber("1879"), new InfiniteNaturalNumber("1889"),
						new InfiniteNaturalNumber("1901"), new InfiniteNaturalNumber("1907"),
						new InfiniteNaturalNumber("1913"), new InfiniteNaturalNumber("1931"),
						new InfiniteNaturalNumber("1933"), new InfiniteNaturalNumber("1949"),
						new InfiniteNaturalNumber("1951"), new InfiniteNaturalNumber("1973"),
						new InfiniteNaturalNumber("1979"), new InfiniteNaturalNumber("1987"),
						new InfiniteNaturalNumber("1993"), new InfiniteNaturalNumber("1997"),
						new InfiniteNaturalNumber("1999"));
				expectedPrimeNumber = new InfiniteNaturalNumber("2003");
			}

		}

	}

	public class FindPrimeNumbersUpTo extends Scenario {

		private InfiniteNaturalNumber maxNumberToTest;
		private List<InfiniteNaturalNumber> expectedPrimeNumbers;

		protected void computeAndCheck() {
			List<InfiniteNaturalNumber> primeNumbersFound = PrimeNumbersCalculator
					.findPrimeNumbersUpTo(maxNumberToTest);

			if (expectedPrimeNumbers.isEmpty()) {
				assertThat(primeNumbersFound, is(empty()));
			} else {
				assertThat(primeNumbersFound, containsExactlyAll(expectedPrimeNumbers));
			}
		}

		public class TestResult extends Scenario {

			@After
			public void after() {
				computeAndCheck();
			}

			@Test
			public void ZERO_returnsEmpty() {
				maxNumberToTest = ZERO;
				expectedPrimeNumbers = CollectionUtils.emptyList();
			}

			@Test
			public void One_returnsEmpty() {
				maxNumberToTest = ONE;
				expectedPrimeNumbers = CollectionUtils.emptyList();
			}

			@Test
			public void Two_returns_2() {
				maxNumberToTest = TWO;
				expectedPrimeNumbers = CollectionUtils.asList(TWO);
			}

			@Test
			public void Three_returns_2_3() {
				maxNumberToTest = THREE;
				expectedPrimeNumbers = CollectionUtils.asList(TWO, THREE);
			}

			@Test
			public void Four_returns_2_3() {
				maxNumberToTest = FOUR;
				expectedPrimeNumbers = CollectionUtils.asList(TWO, THREE);
			}

			@Test
			public void Eleven_returns_2_3_5_7_11() {
				maxNumberToTest = ELEVEN;
				expectedPrimeNumbers = CollectionUtils.asList(TWO, THREE, FIVE, SEVEN, ELEVEN);
			}

			@Test
			public void Twelve_returns_2_3_5_7_11() {
				maxNumberToTest = TWELVE;
				expectedPrimeNumbers = CollectionUtils.asList(TWO, THREE, FIVE, SEVEN, ELEVEN);
			}
		}

		public class PerfTest extends PerfTestScenario {
			@After
			public void after() {
				computeAndCheck();
				System.out.println("FindPrimeNumbersUpTo:" + maxNumberToTest + " was calculated in "
						+ FormatterUtils.GetDurationAsString(getTestDuration()));
			}

			@Test
			public void _101_returns_2_3_5_7_11_13_17_19_23_29_31_37_41_43_47_53_59_61_67_71_73_79_83_89_97() {
				maxNumberToTest = new InfiniteNaturalNumber("101");
				expectedPrimeNumbers = CollectionUtils.asList(TWO, THREE, FIVE, SEVEN, ELEVEN,
						new InfiniteNaturalNumber("13"), new InfiniteNaturalNumber("17"),
						new InfiniteNaturalNumber("19"), new InfiniteNaturalNumber("23"),
						new InfiniteNaturalNumber("29"), new InfiniteNaturalNumber("31"),
						new InfiniteNaturalNumber("37"), new InfiniteNaturalNumber("41"),
						new InfiniteNaturalNumber("43"), new InfiniteNaturalNumber("47"),
						new InfiniteNaturalNumber("53"), new InfiniteNaturalNumber("59"),
						new InfiniteNaturalNumber("61"), new InfiniteNaturalNumber("67"),
						new InfiniteNaturalNumber("71"), new InfiniteNaturalNumber("73"),
						new InfiniteNaturalNumber("79"), new InfiniteNaturalNumber("83"),
						new InfiniteNaturalNumber("89"), new InfiniteNaturalNumber("97"),
						new InfiniteNaturalNumber("101"));
			}

			@Test
			public void _1010_returns_2_3_5_7_11_13_17_19_23_29_31_37_41_43_47_53_59_61_67_71_73_79_83_89_97_101_103_107_109_113_127_131_137_139_149_151_157_163_167_173_179_181_191_193_197_199_211_223_227_229_233_239_241_251_257_263_269_271_277_281_283_293_307_311_313_317_331_337_347_349_353_359_367_373_379_383_389_397_401_409_419_421_431_433_439_443_449_457_461_463_467_479_487_491_499_503_509_521_523_541_547_557_563_569_571_577_587_593_599_601_607_613_617_619_631_641_643_647_653_659_661_673_677_683_691_701_709_719_727_733_739_743_751_757_761_769_773_787_797_809_811_821_823_827_829_839_853_857_859_863_877_881_883_887_907_911_919_929_937_941_947_953_967_971_977_983_991_997_1009() {
				maxNumberToTest = new InfiniteNaturalNumber("1010");
				expectedPrimeNumbers = CollectionUtils.asList(new InfiniteNaturalNumber("2"),
						new InfiniteNaturalNumber("3"), new InfiniteNaturalNumber("5"), new InfiniteNaturalNumber("7"),
						new InfiniteNaturalNumber("11"), new InfiniteNaturalNumber("13"),
						new InfiniteNaturalNumber("17"), new InfiniteNaturalNumber("19"),
						new InfiniteNaturalNumber("23"), new InfiniteNaturalNumber("29"),
						new InfiniteNaturalNumber("31"), new InfiniteNaturalNumber("37"),
						new InfiniteNaturalNumber("41"), new InfiniteNaturalNumber("43"),
						new InfiniteNaturalNumber("47"), new InfiniteNaturalNumber("53"),
						new InfiniteNaturalNumber("59"), new InfiniteNaturalNumber("61"),
						new InfiniteNaturalNumber("67"), new InfiniteNaturalNumber("71"),
						new InfiniteNaturalNumber("73"), new InfiniteNaturalNumber("79"),
						new InfiniteNaturalNumber("83"), new InfiniteNaturalNumber("89"),
						new InfiniteNaturalNumber("97"), new InfiniteNaturalNumber("101"),
						new InfiniteNaturalNumber("103"), new InfiniteNaturalNumber("107"),
						new InfiniteNaturalNumber("109"), new InfiniteNaturalNumber("113"),
						new InfiniteNaturalNumber("127"), new InfiniteNaturalNumber("131"),
						new InfiniteNaturalNumber("137"), new InfiniteNaturalNumber("139"),
						new InfiniteNaturalNumber("149"), new InfiniteNaturalNumber("151"),
						new InfiniteNaturalNumber("157"), new InfiniteNaturalNumber("163"),
						new InfiniteNaturalNumber("167"), new InfiniteNaturalNumber("173"),
						new InfiniteNaturalNumber("179"), new InfiniteNaturalNumber("181"),
						new InfiniteNaturalNumber("191"), new InfiniteNaturalNumber("193"),
						new InfiniteNaturalNumber("197"), new InfiniteNaturalNumber("199"),
						new InfiniteNaturalNumber("211"), new InfiniteNaturalNumber("223"),
						new InfiniteNaturalNumber("227"), new InfiniteNaturalNumber("229"),
						new InfiniteNaturalNumber("233"), new InfiniteNaturalNumber("239"),
						new InfiniteNaturalNumber("241"), new InfiniteNaturalNumber("251"),
						new InfiniteNaturalNumber("257"), new InfiniteNaturalNumber("263"),
						new InfiniteNaturalNumber("269"), new InfiniteNaturalNumber("271"),
						new InfiniteNaturalNumber("277"), new InfiniteNaturalNumber("281"),
						new InfiniteNaturalNumber("283"), new InfiniteNaturalNumber("293"),
						new InfiniteNaturalNumber("307"), new InfiniteNaturalNumber("311"),
						new InfiniteNaturalNumber("313"), new InfiniteNaturalNumber("317"),
						new InfiniteNaturalNumber("331"), new InfiniteNaturalNumber("337"),
						new InfiniteNaturalNumber("347"), new InfiniteNaturalNumber("349"),
						new InfiniteNaturalNumber("353"), new InfiniteNaturalNumber("359"),
						new InfiniteNaturalNumber("367"), new InfiniteNaturalNumber("373"),
						new InfiniteNaturalNumber("379"), new InfiniteNaturalNumber("383"),
						new InfiniteNaturalNumber("389"), new InfiniteNaturalNumber("397"),
						new InfiniteNaturalNumber("401"), new InfiniteNaturalNumber("409"),
						new InfiniteNaturalNumber("419"), new InfiniteNaturalNumber("421"),
						new InfiniteNaturalNumber("431"), new InfiniteNaturalNumber("433"),
						new InfiniteNaturalNumber("439"), new InfiniteNaturalNumber("443"),
						new InfiniteNaturalNumber("449"), new InfiniteNaturalNumber("457"),
						new InfiniteNaturalNumber("461"), new InfiniteNaturalNumber("463"),
						new InfiniteNaturalNumber("467"), new InfiniteNaturalNumber("479"),
						new InfiniteNaturalNumber("487"), new InfiniteNaturalNumber("491"),
						new InfiniteNaturalNumber("499"), new InfiniteNaturalNumber("503"),
						new InfiniteNaturalNumber("509"), new InfiniteNaturalNumber("521"),
						new InfiniteNaturalNumber("523"), new InfiniteNaturalNumber("541"),
						new InfiniteNaturalNumber("547"), new InfiniteNaturalNumber("557"),
						new InfiniteNaturalNumber("563"), new InfiniteNaturalNumber("569"),
						new InfiniteNaturalNumber("571"), new InfiniteNaturalNumber("577"),
						new InfiniteNaturalNumber("587"), new InfiniteNaturalNumber("593"),
						new InfiniteNaturalNumber("599"), new InfiniteNaturalNumber("601"),
						new InfiniteNaturalNumber("607"), new InfiniteNaturalNumber("613"),
						new InfiniteNaturalNumber("617"), new InfiniteNaturalNumber("619"),
						new InfiniteNaturalNumber("631"), new InfiniteNaturalNumber("641"),
						new InfiniteNaturalNumber("643"), new InfiniteNaturalNumber("647"),
						new InfiniteNaturalNumber("653"), new InfiniteNaturalNumber("659"),
						new InfiniteNaturalNumber("661"), new InfiniteNaturalNumber("673"),
						new InfiniteNaturalNumber("677"), new InfiniteNaturalNumber("683"),
						new InfiniteNaturalNumber("691"), new InfiniteNaturalNumber("701"),
						new InfiniteNaturalNumber("709"), new InfiniteNaturalNumber("719"),
						new InfiniteNaturalNumber("727"), new InfiniteNaturalNumber("733"),
						new InfiniteNaturalNumber("739"), new InfiniteNaturalNumber("743"),
						new InfiniteNaturalNumber("751"), new InfiniteNaturalNumber("757"),
						new InfiniteNaturalNumber("761"), new InfiniteNaturalNumber("769"),
						new InfiniteNaturalNumber("773"), new InfiniteNaturalNumber("787"),
						new InfiniteNaturalNumber("797"), new InfiniteNaturalNumber("809"),
						new InfiniteNaturalNumber("811"), new InfiniteNaturalNumber("821"),
						new InfiniteNaturalNumber("823"), new InfiniteNaturalNumber("827"),
						new InfiniteNaturalNumber("829"), new InfiniteNaturalNumber("839"),
						new InfiniteNaturalNumber("853"), new InfiniteNaturalNumber("857"),
						new InfiniteNaturalNumber("859"), new InfiniteNaturalNumber("863"),
						new InfiniteNaturalNumber("877"), new InfiniteNaturalNumber("881"),
						new InfiniteNaturalNumber("883"), new InfiniteNaturalNumber("887"),
						new InfiniteNaturalNumber("907"), new InfiniteNaturalNumber("911"),
						new InfiniteNaturalNumber("919"), new InfiniteNaturalNumber("929"),
						new InfiniteNaturalNumber("937"), new InfiniteNaturalNumber("941"),
						new InfiniteNaturalNumber("947"), new InfiniteNaturalNumber("953"),
						new InfiniteNaturalNumber("967"), new InfiniteNaturalNumber("971"),
						new InfiniteNaturalNumber("977"), new InfiniteNaturalNumber("983"),
						new InfiniteNaturalNumber("991"), new InfiniteNaturalNumber("997"),
						new InfiniteNaturalNumber("1009"));
			}

			@Test
			public void _2002_returns_2_3_5_7_11_13_17_19_23_29_31_37_41_43_47_etc() {
				maxNumberToTest = new InfiniteNaturalNumber("2002");
				expectedPrimeNumbers = CollectionUtils.asList(new InfiniteNaturalNumber("2"),
						new InfiniteNaturalNumber("3"), new InfiniteNaturalNumber("5"), new InfiniteNaturalNumber("7"),
						new InfiniteNaturalNumber("11"), new InfiniteNaturalNumber("13"),
						new InfiniteNaturalNumber("17"), new InfiniteNaturalNumber("19"),
						new InfiniteNaturalNumber("23"), new InfiniteNaturalNumber("29"),
						new InfiniteNaturalNumber("31"), new InfiniteNaturalNumber("37"),
						new InfiniteNaturalNumber("41"), new InfiniteNaturalNumber("43"),
						new InfiniteNaturalNumber("47"), new InfiniteNaturalNumber("53"),
						new InfiniteNaturalNumber("59"), new InfiniteNaturalNumber("61"),
						new InfiniteNaturalNumber("67"), new InfiniteNaturalNumber("71"),
						new InfiniteNaturalNumber("73"), new InfiniteNaturalNumber("79"),
						new InfiniteNaturalNumber("83"), new InfiniteNaturalNumber("89"),
						new InfiniteNaturalNumber("97"), new InfiniteNaturalNumber("101"),
						new InfiniteNaturalNumber("103"), new InfiniteNaturalNumber("107"),
						new InfiniteNaturalNumber("109"), new InfiniteNaturalNumber("113"),
						new InfiniteNaturalNumber("127"), new InfiniteNaturalNumber("131"),
						new InfiniteNaturalNumber("137"), new InfiniteNaturalNumber("139"),
						new InfiniteNaturalNumber("149"), new InfiniteNaturalNumber("151"),
						new InfiniteNaturalNumber("157"), new InfiniteNaturalNumber("163"),
						new InfiniteNaturalNumber("167"), new InfiniteNaturalNumber("173"),
						new InfiniteNaturalNumber("179"), new InfiniteNaturalNumber("181"),
						new InfiniteNaturalNumber("191"), new InfiniteNaturalNumber("193"),
						new InfiniteNaturalNumber("197"), new InfiniteNaturalNumber("199"),
						new InfiniteNaturalNumber("211"), new InfiniteNaturalNumber("223"),
						new InfiniteNaturalNumber("227"), new InfiniteNaturalNumber("229"),
						new InfiniteNaturalNumber("233"), new InfiniteNaturalNumber("239"),
						new InfiniteNaturalNumber("241"), new InfiniteNaturalNumber("251"),
						new InfiniteNaturalNumber("257"), new InfiniteNaturalNumber("263"),
						new InfiniteNaturalNumber("269"), new InfiniteNaturalNumber("271"),
						new InfiniteNaturalNumber("277"), new InfiniteNaturalNumber("281"),
						new InfiniteNaturalNumber("283"), new InfiniteNaturalNumber("293"),
						new InfiniteNaturalNumber("307"), new InfiniteNaturalNumber("311"),
						new InfiniteNaturalNumber("313"), new InfiniteNaturalNumber("317"),
						new InfiniteNaturalNumber("331"), new InfiniteNaturalNumber("337"),
						new InfiniteNaturalNumber("347"), new InfiniteNaturalNumber("349"),
						new InfiniteNaturalNumber("353"), new InfiniteNaturalNumber("359"),
						new InfiniteNaturalNumber("367"), new InfiniteNaturalNumber("373"),
						new InfiniteNaturalNumber("379"), new InfiniteNaturalNumber("383"),
						new InfiniteNaturalNumber("389"), new InfiniteNaturalNumber("397"),
						new InfiniteNaturalNumber("401"), new InfiniteNaturalNumber("409"),
						new InfiniteNaturalNumber("419"), new InfiniteNaturalNumber("421"),
						new InfiniteNaturalNumber("431"), new InfiniteNaturalNumber("433"),
						new InfiniteNaturalNumber("439"), new InfiniteNaturalNumber("443"),
						new InfiniteNaturalNumber("449"), new InfiniteNaturalNumber("457"),
						new InfiniteNaturalNumber("461"), new InfiniteNaturalNumber("463"),
						new InfiniteNaturalNumber("467"), new InfiniteNaturalNumber("479"),
						new InfiniteNaturalNumber("487"), new InfiniteNaturalNumber("491"),
						new InfiniteNaturalNumber("499"), new InfiniteNaturalNumber("503"),
						new InfiniteNaturalNumber("509"), new InfiniteNaturalNumber("521"),
						new InfiniteNaturalNumber("523"), new InfiniteNaturalNumber("541"),
						new InfiniteNaturalNumber("547"), new InfiniteNaturalNumber("557"),
						new InfiniteNaturalNumber("563"), new InfiniteNaturalNumber("569"),
						new InfiniteNaturalNumber("571"), new InfiniteNaturalNumber("577"),
						new InfiniteNaturalNumber("587"), new InfiniteNaturalNumber("593"),
						new InfiniteNaturalNumber("599"), new InfiniteNaturalNumber("601"),
						new InfiniteNaturalNumber("607"), new InfiniteNaturalNumber("613"),
						new InfiniteNaturalNumber("617"), new InfiniteNaturalNumber("619"),
						new InfiniteNaturalNumber("631"), new InfiniteNaturalNumber("641"),
						new InfiniteNaturalNumber("643"), new InfiniteNaturalNumber("647"),
						new InfiniteNaturalNumber("653"), new InfiniteNaturalNumber("659"),
						new InfiniteNaturalNumber("661"), new InfiniteNaturalNumber("673"),
						new InfiniteNaturalNumber("677"), new InfiniteNaturalNumber("683"),
						new InfiniteNaturalNumber("691"), new InfiniteNaturalNumber("701"),
						new InfiniteNaturalNumber("709"), new InfiniteNaturalNumber("719"),
						new InfiniteNaturalNumber("727"), new InfiniteNaturalNumber("733"),
						new InfiniteNaturalNumber("739"), new InfiniteNaturalNumber("743"),
						new InfiniteNaturalNumber("751"), new InfiniteNaturalNumber("757"),
						new InfiniteNaturalNumber("761"), new InfiniteNaturalNumber("769"),
						new InfiniteNaturalNumber("773"), new InfiniteNaturalNumber("787"),
						new InfiniteNaturalNumber("797"), new InfiniteNaturalNumber("809"),
						new InfiniteNaturalNumber("811"), new InfiniteNaturalNumber("821"),
						new InfiniteNaturalNumber("823"), new InfiniteNaturalNumber("827"),
						new InfiniteNaturalNumber("829"), new InfiniteNaturalNumber("839"),
						new InfiniteNaturalNumber("853"), new InfiniteNaturalNumber("857"),
						new InfiniteNaturalNumber("859"), new InfiniteNaturalNumber("863"),
						new InfiniteNaturalNumber("877"), new InfiniteNaturalNumber("881"),
						new InfiniteNaturalNumber("883"), new InfiniteNaturalNumber("887"),
						new InfiniteNaturalNumber("907"), new InfiniteNaturalNumber("911"),
						new InfiniteNaturalNumber("919"), new InfiniteNaturalNumber("929"),
						new InfiniteNaturalNumber("937"), new InfiniteNaturalNumber("941"),
						new InfiniteNaturalNumber("947"), new InfiniteNaturalNumber("953"),
						new InfiniteNaturalNumber("967"), new InfiniteNaturalNumber("971"),
						new InfiniteNaturalNumber("977"), new InfiniteNaturalNumber("983"),
						new InfiniteNaturalNumber("991"), new InfiniteNaturalNumber("997"),
						new InfiniteNaturalNumber("1009"), new InfiniteNaturalNumber("1013"),
						new InfiniteNaturalNumber("1019"), new InfiniteNaturalNumber("1021"),
						new InfiniteNaturalNumber("1031"), new InfiniteNaturalNumber("1033"),
						new InfiniteNaturalNumber("1039"), new InfiniteNaturalNumber("1049"),
						new InfiniteNaturalNumber("1051"), new InfiniteNaturalNumber("1061"),
						new InfiniteNaturalNumber("1063"), new InfiniteNaturalNumber("1069"),
						new InfiniteNaturalNumber("1087"), new InfiniteNaturalNumber("1091"),
						new InfiniteNaturalNumber("1093"), new InfiniteNaturalNumber("1097"),
						new InfiniteNaturalNumber("1103"), new InfiniteNaturalNumber("1109"),
						new InfiniteNaturalNumber("1117"), new InfiniteNaturalNumber("1123"),
						new InfiniteNaturalNumber("1129"), new InfiniteNaturalNumber("1151"),
						new InfiniteNaturalNumber("1153"), new InfiniteNaturalNumber("1163"),
						new InfiniteNaturalNumber("1171"), new InfiniteNaturalNumber("1181"),
						new InfiniteNaturalNumber("1187"), new InfiniteNaturalNumber("1193"),
						new InfiniteNaturalNumber("1201"), new InfiniteNaturalNumber("1213"),
						new InfiniteNaturalNumber("1217"), new InfiniteNaturalNumber("1223"),
						new InfiniteNaturalNumber("1229"), new InfiniteNaturalNumber("1231"),
						new InfiniteNaturalNumber("1237"), new InfiniteNaturalNumber("1249"),
						new InfiniteNaturalNumber("1259"), new InfiniteNaturalNumber("1277"),
						new InfiniteNaturalNumber("1279"), new InfiniteNaturalNumber("1283"),
						new InfiniteNaturalNumber("1289"), new InfiniteNaturalNumber("1291"),
						new InfiniteNaturalNumber("1297"), new InfiniteNaturalNumber("1301"),
						new InfiniteNaturalNumber("1303"), new InfiniteNaturalNumber("1307"),
						new InfiniteNaturalNumber("1319"), new InfiniteNaturalNumber("1321"),
						new InfiniteNaturalNumber("1327"), new InfiniteNaturalNumber("1361"),
						new InfiniteNaturalNumber("1367"), new InfiniteNaturalNumber("1373"),
						new InfiniteNaturalNumber("1381"), new InfiniteNaturalNumber("1399"),
						new InfiniteNaturalNumber("1409"), new InfiniteNaturalNumber("1423"),
						new InfiniteNaturalNumber("1427"), new InfiniteNaturalNumber("1429"),
						new InfiniteNaturalNumber("1433"), new InfiniteNaturalNumber("1439"),
						new InfiniteNaturalNumber("1447"), new InfiniteNaturalNumber("1451"),
						new InfiniteNaturalNumber("1453"), new InfiniteNaturalNumber("1459"),
						new InfiniteNaturalNumber("1471"), new InfiniteNaturalNumber("1481"),
						new InfiniteNaturalNumber("1483"), new InfiniteNaturalNumber("1487"),
						new InfiniteNaturalNumber("1489"), new InfiniteNaturalNumber("1493"),
						new InfiniteNaturalNumber("1499"), new InfiniteNaturalNumber("1511"),
						new InfiniteNaturalNumber("1523"), new InfiniteNaturalNumber("1531"),
						new InfiniteNaturalNumber("1543"), new InfiniteNaturalNumber("1549"),
						new InfiniteNaturalNumber("1553"), new InfiniteNaturalNumber("1559"),
						new InfiniteNaturalNumber("1567"), new InfiniteNaturalNumber("1571"),
						new InfiniteNaturalNumber("1579"), new InfiniteNaturalNumber("1583"),
						new InfiniteNaturalNumber("1597"), new InfiniteNaturalNumber("1601"),
						new InfiniteNaturalNumber("1607"), new InfiniteNaturalNumber("1609"),
						new InfiniteNaturalNumber("1613"), new InfiniteNaturalNumber("1619"),
						new InfiniteNaturalNumber("1621"), new InfiniteNaturalNumber("1627"),
						new InfiniteNaturalNumber("1637"), new InfiniteNaturalNumber("1657"),
						new InfiniteNaturalNumber("1663"), new InfiniteNaturalNumber("1667"),
						new InfiniteNaturalNumber("1669"), new InfiniteNaturalNumber("1693"),
						new InfiniteNaturalNumber("1697"), new InfiniteNaturalNumber("1699"),
						new InfiniteNaturalNumber("1709"), new InfiniteNaturalNumber("1721"),
						new InfiniteNaturalNumber("1723"), new InfiniteNaturalNumber("1733"),
						new InfiniteNaturalNumber("1741"), new InfiniteNaturalNumber("1747"),
						new InfiniteNaturalNumber("1753"), new InfiniteNaturalNumber("1759"),
						new InfiniteNaturalNumber("1777"), new InfiniteNaturalNumber("1783"),
						new InfiniteNaturalNumber("1787"), new InfiniteNaturalNumber("1789"),
						new InfiniteNaturalNumber("1801"), new InfiniteNaturalNumber("1811"),
						new InfiniteNaturalNumber("1823"), new InfiniteNaturalNumber("1831"),
						new InfiniteNaturalNumber("1847"), new InfiniteNaturalNumber("1861"),
						new InfiniteNaturalNumber("1867"), new InfiniteNaturalNumber("1871"),
						new InfiniteNaturalNumber("1873"), new InfiniteNaturalNumber("1877"),
						new InfiniteNaturalNumber("1879"), new InfiniteNaturalNumber("1889"),
						new InfiniteNaturalNumber("1901"), new InfiniteNaturalNumber("1907"),
						new InfiniteNaturalNumber("1913"), new InfiniteNaturalNumber("1931"),
						new InfiniteNaturalNumber("1933"), new InfiniteNaturalNumber("1949"),
						new InfiniteNaturalNumber("1951"), new InfiniteNaturalNumber("1973"),
						new InfiniteNaturalNumber("1979"), new InfiniteNaturalNumber("1987"),
						new InfiniteNaturalNumber("1993"), new InfiniteNaturalNumber("1997"),
						new InfiniteNaturalNumber("1999"));
			}
		}
	}

}
