package simulator.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.json.*;
import simulator.factories.Factory;
import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.PhysicsSimulator;

public class Controller {

	private PhysicsSimulator simulator;
	private Factory<ForceLaws> forceLawsFactory;
	private Factory<Body> bodiesFactory;

	public Controller(PhysicsSimulator simulator, Factory<ForceLaws> forceLawsFactory, Factory<Body> bodiesFactory) {
		this.simulator = simulator;
		this.forceLawsFactory = forceLawsFactory;
		this.bodiesFactory = bodiesFactory;
	}

	public void loadData(InputStream in) {
		JSONObject jsonInput = new JSONObject(new JSONTokener(in));

		if (jsonInput.has("groups")) {
			JSONArray groupsArray = jsonInput.getJSONArray("groups");
			for (int i = 0; i < groupsArray.length(); i++) {
				simulator.addGroup(groupsArray.getString(i));
			}
		}

		if (jsonInput.has("laws")) {
			JSONArray lawsArray = jsonInput.getJSONArray("laws");
			for (int i = 0; i < lawsArray.length(); i++) {
				JSONObject lawObject = lawsArray.getJSONObject(i);
				String groupId = lawObject.getString("id");
				JSONObject lawData = lawObject.getJSONObject("laws");
				ForceLaws instanceForceLaws = forceLawsFactory.createInstance(lawData);
				simulator.setForceLaws(groupId, instanceForceLaws);
			}
		}

		if (jsonInput.has("bodies")) {
			JSONArray bodiesArray = jsonInput.getJSONArray("bodies");
			for (int i = 0; i < bodiesArray.length(); i++) {
				JSONObject bodyObject = bodiesArray.getJSONObject(i);
				Body instanceBody = bodiesFactory.createInstance(bodyObject);
				simulator.addBody(instanceBody);
			}
		}

	}

	public void run(int n, OutputStream out) throws IOException {
		JSONArray statesArray = new JSONArray();
		statesArray.put(simulator.getState());

		for (int i = 1; i <= n; i++) {
			simulator.advance();
			statesArray.put(simulator.getState());
		}

		JSONObject outputObject = new JSONObject();
		outputObject.put("states", statesArray);

		out.write(outputObject.toString().getBytes());
	}
}
