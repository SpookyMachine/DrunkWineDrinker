package com.powerbot.task;

import java.util.concurrent.Callable;
import java.util.logging.Logger;

import org.powerbot.script.Condition;
import org.powerbot.script.Filter;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Item;

import com.powerbot.winedrinker.Jugs;

public class DrinkWineTask extends Task<ClientContext> {

	private final static Logger log = Logger.getLogger(DrinkWineTask.class
			.getName());

	public DrinkWineTask(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {

		log.info("Checking if where is any wine to drink");
		// Check if back back contains Jugs of Wine
		// TODO: Change water to wine
		System.out.println(ctx.backpack.select());
		if (!ctx.backpack.select(JugsFilter).isEmpty()) {
			log.info("Wine found in back pack");
			return true;
		}
		return false;
	}

	@Override
	public void execute() {

		log.info("Tryng to drink all wine in the back pack");

		for (Item selectedItem : ctx.backpack.select(JugsFilter)) {

			selectedItem.interact("Empty");

			// Convert to lambda??

			Condition.wait(new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {

					if (!selectedItem.valid())
						return true;

					return false;
				}
			});

		}

	}

	public final Filter<Item> JugsFilter = new Filter<Item>() {

		@Override
		public boolean accept(Item item) {

			if (item.id() == Jugs.WATER.getCode()) {
				return true;
			}

			return false;
		}

	};
}