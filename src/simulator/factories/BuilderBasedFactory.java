package simulator.factories;

import org.json.JSONObject;

import java.util.*;

public class BuilderBasedFactory<T> implements Factory<T> {

	private Map<String, Builder<T>> _builders;
	private List<JSONObject> _buildersInfo;

	public BuilderBasedFactory() {
		_builders = new HashMap<>();
		_buildersInfo = new LinkedList<>();
	}

	public BuilderBasedFactory(List<Builder<T>> builders) {
		this();
		for (Builder<T> builder : builders) {
			addBuilder(builder);
		}
	}

	public void addBuilder(Builder<T> b) {
		_builders.put(b.getTypeTag(), b);
		_buildersInfo.add(b.getInfo());
	}

	@Override
	public T createInstance(JSONObject info) {
		if (info == null) {
			throw new IllegalArgumentException("Invalid value: null");
		}

		// Search for a builder with a tag equals to info . getString ("type"), call its
		// createInstance method and return the result if it is not null . The value you
		// pass to createInstance is :
		//
		// info . has("data") ? info . getJSONObject("data") : new getJSONObject()

		String typeTag = info.getString("type");
		JSONObject data = info.has("data") ? info.getJSONObject("data") : new JSONObject();
		Builder<T> builder = _builders.get(typeTag);
		if (builder == null) {
			throw new IllegalArgumentException("Builder not found for: " + typeTag);
		}
		T instance = builder.createInstance(data);
		if (instance == null) {
			throw new IllegalArgumentException("Failed to create instance for: " + typeTag);
		}
		return instance;
	}

	@Override
	public List<JSONObject> getInfo() {
		return Collections.unmodifiableList(_buildersInfo);
	}
}
