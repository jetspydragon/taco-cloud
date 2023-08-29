package tacos;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.extern.slf4j.Slf4j;
import tacos.Ingredient.Type;

/**
 * SessionAttributes("tacoOrder")
 * This indicates that the TacoOrder object that is put into the model
 * should be maintained in session.
 */
@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {

	/**
	 * Este método es llamado, debido a la anotación, cuando la request es manejada.
	 * Crea una lista de ingredientes para cargar al modelo. 'model' es un objeto que
	 * transporta los datos desde el Controller a la vista, seal cual fuera la vista 
	 * encargada de renderizar los datos.
	 * @param model
	 */
	@ModelAttribute
	public void addIngredientsToModel(Model model) {
		List<Ingredient> ingredients = Arrays.asList(
			new Ingredient("FLTO", "Flour tortilla", Type.WRAP),
			new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
			new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
			new Ingredient("CARN", "Carnitas", Type.PROTEIN),
			new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
			new Ingredient("LETC", "Lettuce", Type.VEGGIES),
			new Ingredient("CHED", "Cheddar", Type.CHEESE),
			new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
			new Ingredient("SLSA", "Salsa", Type.SAUCE),
			new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
		);
		
		Type[] types = Ingredient.Type.values();
		for (Type type : types) {
			model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
		}
	}
	
	/**
	 * Agrega el objeto 'tacoOrder' al modelo
	 * @return
	 */
	@ModelAttribute(name = "tacoOrder")
	public TacoOrder order() {
		return new TacoOrder();
	}
	
	/**
	 * Agrega el objeto 'taco' al modelo
	 * @return
	 */
	@ModelAttribute(name = "taco")
	public Taco taco() {
		return new Taco();
	}
	
	/**
	 * Este método será el que se invoque en el http request
	 * @return Nombre de la plantilla
	 */
	@GetMapping
	public String showDesignForm() {
		return "design";
	}
	
	private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
		return ingredients.stream()
				.filter(x -> x.getType().equals(type))
				.collect(Collectors.toList());
	}
}
