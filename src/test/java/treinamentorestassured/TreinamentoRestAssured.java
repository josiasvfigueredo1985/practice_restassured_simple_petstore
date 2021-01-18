package treinamentorestassured;

import com.aventstack.extentreports.*;
import org.testng.*;
import org.testng.annotations.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;
import org.json.simple.*;
import org.testng.reporters.*;
import java.io.*;
import java.lang.reflect.*;
import java.util.*;



public class TreinamentoRestAssured {

//    @BeforeSuite
//    public void beforSuite() {
//        EXTENT_REPORT = new ExtentReports();
//        HTML_REPORTER = new ExtentHtmlReporter(reportPath+"/"+fileName);
//        EXTENT_REPORT.attachReporter(HTML_REPORTER);
//    }

    @Test
    public void helloWorld() {
        given().
                baseUri("http://petstore.swagger.io/v2").
                basePath("/pet/{petId}").
                pathParam("petId", 99998).
                when().
                get().
                then().
                statusCode(404);
    }

    @Test
    public void cadastraNovoPetComSucesso() {

        // Json Simple
/*
        JSONObject pet = new JSONObject();
        JSONObject category = new JSONObject();
        JSONObject tag1 = new JSONObject();
        JSONObject tag2 = new JSONObject();
        JSONArray tags = new JSONArray();
        JSONArray photoURLs = new JSONArray();

        pet.put("id", 1);
        pet.put("name", "Shepherd");
        pet.put("status", "available");
        category.put("id", 1);
        category.put("name", "felino");
        pet.put("category", category);
        tag1.put("id", 1);
        tag1.put("name", "Sem raça definida");
        tag2.put("id", 1);
        tag2.put("name", "Amarelo");
        tags.add(tag1);
        tags.add(tag2);
        pet.put("tags", tags);
        photoURLs.add("fotosdegato.com.br/foto1.png");
        photoURLs.add("fotosdegato.com.br/foto2.png");
        pet.put("photoUrls", photoURLs);


 */
/// Objetos das classes criadas
        Pet pet = new Pet();
        pet.setId(111);
        Category category = new Category();
        category.setId(111);
        category.setName("felinos");
        pet.setCategory(category);
        pet.setName("Shepherd");
        pet.setPhotoUrls(new String[]{"http://fotosdegato.com.br/foto1.png", "http://fotosdegato.com.br/foto2.png"});
        Tags tag1 = new Tags();
        tag1.setId(111);
        tag1.setName("Sem raça definida");
        Tags tag2 = new Tags();
        tag2.setId(111);
        tag2.setName("Amarelo");
        pet.setTags(new Tags[]{tag1, tag2});
        pet.setStatus("available");

        given()
                .baseUri("https://petstore.swagger.io/v2")
                .basePath("/pet")
                .header("content-type", "application/json")
                .body(pet)
                .when()
                .post()
                .then()
                .statusCode(200)
                .body("id", equalTo(111),
                        "name", equalTo("Shepherd"),
                        "tags[0].name", equalTo("Sem raça definida"),
                        "category.name", equalTo("felinos"));

    }

    @Test
    public void CadastrarNovoPedidoDePetComSucesso() {
        ///POST /store/order
        Store store = new Store();
        store.setId(1);//"id": 0,
        store.setPetId(1); // "petId": 0,
        store.setQuantity(1);//"quantity": 0,
        store.setShipDate("2021-01-15");//"shipDate": "2021-01-15T13:07:26.767Z",
        store.setStatus("placed");// "status": "placed",
        store.setComplete(true);//"complete": true

        given().
                baseUri("https://petstore.swagger.io/v2").
                basePath("/store/order").
                header("content-type", "application/json").
                body(store).
                when().
                post().
                then().
                statusCode(200).
                body("id", equalTo(1),
                        "petId", equalTo(1),
                        "quantity", equalTo(1),
                        "status", equalTo("placed"),
                        "complete", equalTo(true)
                );
    }

    @Test
    public void pesquisarPorUmPetInexistente() {
        //GET /pet/{petId}
        given().
                baseUri("https://petstore.swagger.io/v2").
                basePath("/pet/{petId}").
                pathParam("petId", 111000).
                header("content-type", "application/json").
                when().
                get().
                then().
                statusCode(404);

    }

    @Test
    public void atualizarDadosDeUmPetExistente() {
        //PUT /pet
        Pet pet = new Pet();
        pet.setId(111);
        Category category = new Category();
        category.setId(111);
        category.setName("felinos");
        pet.setCategory(category);
        pet.setName("Paçoca");
        pet.setPhotoUrls(new String[]{"http://fotosdegato.com.br/foto1.png", "http://fotosdegato.com.br/foto2.png"});
        Tags tag1 = new Tags();
        tag1.setId(111);
        tag1.setName("Sem raça definida");
        Tags tag2 = new Tags();
        tag2.setId(111);
        tag2.setName("Adotado");
        pet.setTags(new Tags[]{tag1, tag2});
        pet.setStatus("pending");

        given()
                .baseUri("https://petstore.swagger.io/v2")
                .basePath("/pet")
                .header("content-type", "application/json")
                .body(pet)
                .when()
                .post()
                .then()
                .statusCode(200)
                .body("id", equalTo(111),
                        "name", equalTo("Paçoca"),
                        "tags[1].name", equalTo("Adotado"),
                        "category.name", equalTo("felinos"),
                        "status", equalTo("pending"));

    }

    @Test
    public void atualizarDadosDeUmPetInformandoIdComFormatoInválido() {
        //PUT /pet
        Pet2 pet = new Pet2();
        pet.setId("111.");
        Category category = new Category();
        category.setId(111);
        category.setName("felinos");
        pet.setCategory(category);
        pet.setName("Paçoca");
        pet.setPhotoUrls(new String[]{"http://fotosdegato.com.br/foto1.png", "http://fotosdegato.com.br/foto2.png"});
        Tags tag1 = new Tags();
        tag1.setId(111);
        tag1.setName("Sem raça definida");
        Tags tag2 = new Tags();
        tag2.setId(111);
        tag2.setName("Adotado");
        pet.setTags(new Tags[]{tag1, tag2});
        pet.setStatus("available");

        given()
                .baseUri("https://petstore.swagger.io/v2")
                .basePath("/pet")
                .header("content-type", "application/json")
                .body(pet)
                .when()
                .post()
                .then()
                .statusCode(500);
    }

    @Test
    public void pesquisarPorPetsComStatusPending() {
        //GET /pet/findByStatus
        given().
                baseUri("https://petstore.swagger.io/v2").
                basePath("/pet/findByStatus?status={status}").
                pathParam("status", "?pending").
                header("content-type", "application/json").
                when().
                get().
                then().
                statusCode(200);
    }

    @Test
    public void realizarRequisiçãoInformandoMétodoInválido() {
        given().
                baseUri("https://petstore.swagger.io/v2").
                basePath("/pet/findByStatus?status={status}").
                pathParam("status", "?sold").
                header("content-type", "application/json").
                when().
                put().//Put no lugar do get
                then().
                statusCode(405);//Invalid input
    }


////////////////////////////////////////////////--- Data Driven ---///////////////////////////////////////////////////////////////

    @DataProvider(name = "dataPetObjectProvider")
    public Object[] dataPetProvider() {
        //PET1
        Pet pet1 = new Pet();
        pet1.setId(10);
        Category category = new Category();
        category.setId(10);
        category.setName("felino");
        pet1.setCategory(category);
        pet1.setName("Shepherd");
        pet1.setPhotoUrls(new String[]{"http://fotosdegato.com.br/shepherd1.png"});
        Tags tag1 = new Tags();
        tag1.setId(10);
        tag1.setName("Sem raça definida");
        pet1.setTags(new Tags[]{tag1});
        pet1.setStatus("available");

        //PET2
        Pet pet2 = new Pet();
        pet2.setId(20);
        category = new Category();
        category.setId(20);
        category.setName("felino");
        pet2.setCategory(category);
        pet2.setName("Martini");
        pet2.setPhotoUrls(new String[]{"http://fotosdegato.com.br/martini.png"});
        Tags tag2 = new Tags();
        tag2.setId(20);
        tag2.setName("Angorá");
        pet2.setTags(new Tags[]{tag2});
        pet2.setStatus("pending");
        return new Pet[]{pet1, pet2};
    }

    @Test(dataProvider = "dataPetObjectProvider")
    public void testeComDataDriven(Pet petData) {
        given()
                .baseUri("https://petstore.swagger.io/v2")
                .basePath("/pet")
                .header("content-type", "application/json")
                .body(petData)
                .when()
                .post()
                .then()
                .statusCode(200)
                .body("id", equalTo(petData.getId()),
                        "category.id", equalTo(petData.getCategory().getId()),
                        "category.name", equalTo(petData.getCategory().getName()),
                        "name", equalTo(petData.getName()),
                        "photoUrls[0]", equalTo(petData.getPhotoUrls()[0]),
                        "tags[0].id", equalTo(petData.getTags()[0].getId()),
                        "tags[0].name", equalTo(petData.getTags()[0].getName()),
                        "status", equalTo(petData.getStatus()));
    }

//////////////////////////////////////////--- Data Driven com arquivo CSV --- ///////////////////////////////////////

    // Método de leitura do arquivo CSV que receve como parâmetro uma string contendo o caminho do arquivo
    public Iterator<Object []> csvProvider(String csvNamePath){
        String line = "";// Adiciona aspas separando os dados de cada linha lida do arquivo
        String cvsSplitBy = ";";// Separador das colunas
        List<Object []> testCases = new ArrayList<>();
        String[] data= null;

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(csvNamePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                if (!((line = br.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            data= line.split(cvsSplitBy);
            testCases.add(data);
        }
        return testCases.iterator();
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DataProvider(name="dataPetCSVProvider")
    public Iterator<Object []> dataPetProvider2(){
        return csvProvider("src/test/java/treinamentorestassured/petData.csv");
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test(dataProvider = "dataPetCSVProvider")
    public void testeComDataDriven2(String[] petData) {

        // Apontando o dado conforme a posição de array no arquivo CSV
        int petId = Integer.parseInt(petData[0]);
        int categoryId = Integer.parseInt(petData[1]);
        String categoryName = petData[2];
        String petName = petData[3];
        String petPhotoURL1 = petData[4];
        int tag1Id = Integer.parseInt(petData[5]);
        String tag1Name = petData[6];
        String petStatus = petData[7];

        JSONObject pet = new JSONObject();
        JSONObject category = new JSONObject();
        JSONObject tag1 = new JSONObject();
        JSONArray tags = new JSONArray();
        JSONArray photoURLs = new JSONArray();

        pet.put("id", petId);
        category.put("id", categoryId);
        category.put("name", categoryName);
        pet.put("category", category);
        pet.put("name", petName);
        photoURLs.add(petPhotoURL1);
        pet.put("photoUrls", photoURLs);
        tag1.put("id", tag1Id);
        tag1.put("name", tag1Name);
        tags.add(tag1);
        pet.put("tags", tags);
        pet.put("status", petStatus);

        given()
                .baseUri("https://petstore.swagger.io/v2")
                .header("content-type", "application/json")
                .basePath("/pet")
                .body(pet)
                .when()
                .post()
                .then()
                .statusCode(200)
                .body("id", equalTo(petId),
                        "category.id", equalTo(categoryId),
                        "category.name", equalTo(categoryName),
                        "name", equalTo(petName),
                        "photoUrls[0]", equalTo(petPhotoURL1),
                        "tags[0].id", equalTo(tag1Id),
                        "tags[0].name", equalTo(tag1Name),
                        "status", equalTo(petStatus));
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//    public static ExtentReports EXTENT_REPORT = null; //instância do report
//    public static ExtentHtmlReporter HTML_REPORTER = null; //tipo do report que será gerado (html)
//    public static ExtentTest TEST; //objeto que adicionaremos informações sobre os testes
//    public static String reportPath = "target/reports/"; //caminho de onde o arquivo do relatório será salvo
//    public static String fileName = "TreinamentoRestAssured.html"; //nome do relatório


    // Método data provider chama a função que faz a leitura dos dados do CSV
    @DataProvider(name="dataUserCSVProvider")
    public Iterator<Object []> dataUserProvider3(){

      return  csvProvider("src/test/java/treinamentorestassured/userData.csv");

    }

//    @BeforeMethod
//    public void beforeMethod(Method method) {
//        TEST = EXTENT_REPORT.createTest(method.getName());
//    }

    @Test(dataProvider = "dataUserCSVProvider")
    public void testeComDataDriven3(String[] userData) {

        // Apontando o dado conforme a posição de array no arquivo CSV
        // e atribuindo à variável o valor encontrado na posição do arquivo
        int userId = Integer.parseInt(userData[0]);
        String userUsername = userData[1];
        String userFirstName = userData[2];
        String userLastName = userData[3];
        String userEmail = userData[4];
        String userPassword = userData[5];
        String userPhone = userData[6];
        int userStatus = Integer.parseInt(userData[7]);

       JSONObject user = new JSONObject();// Objeto user não possui outros atributos

        //Passando os dados de inserção lidos do csv através de chave valor atribuídos
        user.put("id", userId);
        user.put("username", userUsername);
        user.put("firstName",userFirstName);
        user.put("lastName",userLastName);
        user.put("email",userEmail);
        user.put("password",userPassword);
        user.put("phone",userPhone);
        user.put("userStatus",userStatus);

        given()
                .baseUri("https://petstore.swagger.io/v2")
                .basePath("/user")
                .header("content-type", "application/json")
                .body(user)
        .when()
                .post()
        .then()
                .statusCode(200)//// Validação do response body
                .body("type", equalTo("unknown"),
                        "message", equalTo(Integer.toString(userId)));
    }
//    @AfterMethod
//    public void afterTest(ITestResult result) {
//        switch (result.getStatus())
//        {
//            case ITestResult.FAILURE:
//                TEST.log(Status.FAIL, result.getThrowable().toString());
//                break;
//            case ITestResult.SKIP:
//                TEST.log(Status.SKIP, result.getThrowable().toString());
//                break;
//            default:
//                TEST.log(Status.PASS, "Sucesso");
//                break;
//        }
//    }
//TEST.log(Status.INFO, "informação desejada");
//
//    @AfterSuite
//    public void afterSuite(){
//        EXTENT_REPORT.flush();
//    }
}