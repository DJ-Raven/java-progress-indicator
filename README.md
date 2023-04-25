# java-progress-indicator

Progress Indicator Custom using java swing

### Sample code
``` java
// Init
ProgressIndicator progressIndicator = new ProgressIndicator();
PanelSlider panelSlider = new PanelSlider();

// Create component
Component[] components = new Component[]{new PanelTest(1), new PanelTest(2), new PanelTest(3)};

// Integrate panelslider and progressindicator
panelSlider.setSliderComponent(components);
progressIndicator.initSlider(panelSlider);
```
### Sample code next and previous slider

``` java
progressIndicator.previous();

progressIndicator.next();
```
### Library Use
- TimmingFramework-0.55.jar
- miglayout-4.0.jar
### Screenshot

![s](https://user-images.githubusercontent.com/58245926/234339029-223c0fe7-2c49-4657-9852-b604b5cd4b36.png)
