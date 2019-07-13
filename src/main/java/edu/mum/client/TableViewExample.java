package edu.mum.client;

import java.util.function.Function;

import edu.mum.spreadsheet.Row;
import edu.mum.spreadsheet.SpreadSheet;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class TableViewExample extends Application {

	public static class NumberedCell extends TableCell {

		protected void updateItem(Object object, boolean selected) {
			setText(String.valueOf(getIndex()));
		}
	}

	public static class Person {
		private String firstName;
		private String lastName;

		public String getFirstName() {
			return firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public Person(String firstName, String lastName) {
			super();
			this.firstName = firstName;
			this.lastName = lastName;
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	private <S, T> TableColumn<S, T> createColumn(String title, Function<S, ObservableValue<T>> property,
			double width) {
		TableColumn<S, T> col = new TableColumn<>(title);
		col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
		col.setPrefWidth(width);
		return col;
	}

	@Override
	public void start(Stage primaryStage) {

		TableView<Row> tableView = new TableView<Row>();
		tableView.setEditable(true);
		TableColumn<Row, String> column1 = new TableColumn<>("1");
		column1.setCellFactory(col->{
				TextFieldTableCell<Row, String> cell = new TextFieldTableCell<Row, String>();
				cell.setEditable(true);
//				cell.setText(value);
//				cell.addEventHandler(eventType, eventHandler);
//				cell.addEventHandler( , e->{});
				return cell;
			});
		column1.setOnEditStart(e->{
//			 e.getRowValue().getCell(1).getFormula();
//			e.getTablePosition().get
//			e.getTableView().getItems().get(e.getTablePosition().getRow()).
		});
//		column1.onEditStartProperty().bind(observable);;
		column1.setCellValueFactory(new Callback<CellDataFeatures<Row,String>, ObservableValue<String>>(){

			@Override
			public ObservableStringValue call(CellDataFeatures<Row, String> param) {

				return new ObservableStringValue() {

					@Override
					public String get() {
						return param.getValue().getCell(1).getFormula();
					}

					@Override
					public void addListener(ChangeListener<? super String> listener) {
						
					}

					@Override
					public void removeListener(ChangeListener<? super String> listener) {
						
					}

					@Override
					public String getValue() {
						return param.getValue().getCell(1).getFormula();
					}

					@Override
					public void addListener(InvalidationListener listener) {
						
					}

					@Override
					public void removeListener(InvalidationListener listener) {
						
					}
					
				};
			}
			
		});
//		column1.setCellValueFactory(new PropertyValueFactory<>("firstName"));
//		column1.setCellValueFactory(p->1);
		TableColumn<Row, String> column2 = new TableColumn<>("2");
		column2.setCellValueFactory(new PropertyValueFactory<>("lastName"));

		TableColumn<Row, String> column3 = new TableColumn<>("3");
		column3.setCellFactory(col -> {
			TableCell cell = new TableCell();
			cell.textProperty()
					.bind(Bindings.when(cell.emptyProperty()).then("").otherwise(cell.indexProperty().asString()));
			return cell;
		});
//		column3.setCellFactory(col -> new TableCell<Task, String>() {
//		    @Override
//		    protected void updateIndex(int index) {
//		        super.updateIndex(index);
//		        if (isEmpty() || index < 0) {
//		            setText(null);
//		        } else {
//		            setText(Integer.toString(index));
//		        }
//		    }
//		});

		tableView.getColumns().add(column1);
		tableView.getColumns().add(column2);
		tableView.getColumns().add(column3);
//		tableView.addEventHandler(eventType, eventHandler);
		SpreadSheet sheet = new SpreadSheet();
		Director director = new Director();
		director.fillSampleSheet(sheet);
		
		for (int i = 1; i < 2; i++) {
			tableView.getItems().add(sheet.getRow(i));
		}

		VBox vbox = new VBox(tableView);

		Scene scene = new Scene(vbox);

		primaryStage.setScene(scene);

		primaryStage.show();
	}

}