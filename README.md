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
![2023-04-24_231011](https://user-images.githubusercontent.com/58245926/234370542-08df766e-ffb6-4432-a5f6-541f584c622c.png)
