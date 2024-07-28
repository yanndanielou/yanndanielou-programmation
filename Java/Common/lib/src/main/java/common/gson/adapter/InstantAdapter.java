package common.gson.adapter;

import java.io.IOException;
import java.time.Instant;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class InstantAdapter extends TypeAdapter<Instant> {

	@Override
	public Instant read(JsonReader reader) throws IOException {
		Instant instant = null;

		while (reader.hasNext()) {
			String fieldname = null;
			JsonToken token = reader.peek();

			if (token.equals(JsonToken.NAME)) {
				// get the current token
				fieldname = reader.nextName();
			}

			if ("toString".equals(fieldname)) {
				// move to next token
				token = reader.peek();
				instant = Instant.parse(token.toString());
			}


		}
		reader.endObject();

		return instant;
	}

	@Override
	public void write(JsonWriter writer, Instant instant) throws IOException {
		writer.beginObject();
		writer.name("toString");
		writer.value(instant.toString());
		writer.endObject();
	}

}
