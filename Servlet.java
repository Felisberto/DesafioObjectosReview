package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import desafioObject.resposta.Resposta;


/**
 * Servlet implementation class Servlet
 */
@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String cep = request.getParameter("cep");
		PrintWriter out = response.getWriter();
	
		
		String url = "http://www.buscacep.correios.com.br/servicos/dnec/consultaEnderecoAction.do?relaxation="+ cep +"&TipoCep=LOG&semelhante=N&cfm=1&Metodo=listaLogradouro&TipoConsulta=relaxation&StartRow=1&EndRow=10";
		
		
		
		String teste = new String();
		List <String> lis = new ArrayList<String>();
		
		HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
		
		con.setDoOutput(true);
		
		OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
		wr.write(url);

		
		BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
		
		
			while((teste = rd.readLine()) != null)
					if(teste.contains("<td") && teste.contains("</td>"))
								 lis.add(teste.substring(teste.indexOf(">")+1, teste.indexOf("/")-1));
					
			
		Resposta objectos = new Resposta();
		
			objectos.setLogradouro(lis.get(0));
			objectos.setBairro(lis.get(1));
			objectos.setLocalidade(lis.get(2));
			objectos.setUf(lis.get(3));
			objectos.setCep(lis.get(4));
			
		
		out.println(objectos.getLogradouro());
		out.println(objectos.getBairro());
		out.println(objectos.getLocalidade());
		out.println(objectos.getUf());
		out.println(objectos.getCep());
		
	}

}
