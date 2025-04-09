import SwiftUI
import AppMetricaCore

@main
struct iOSApp: App {
    
    init() {
        let configuration = AppMetricaConfiguration(apiKey: "186e8d86-21c9-4b08-93ee-645df9b652c2")
        AppMetrica.activate(with: configuration!)
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
